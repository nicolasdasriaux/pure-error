package pureerror.cats

import cats.data.{Validated, ValidatedNel}
import cats.implicits._

import pureerror.service._

object ValidatedCustomer {
  def validateTrimmedAndNonEmpty(s: String): Either[String, String] =
    if (s != "" && s.trim == s) Either.right(s) else Either.left("Expected to be trimmed and non empty")

  def validateIntString(s: String): Either[String, Int] =
    if (s.matches("""-?\d+""")) s.toInt.asRight else s"Invalid int string ($s)".asLeft

  def validatePositiveInt(i: Int): Either[String, Int] =
    Either.cond(i > 0, i, s"Negative int ($i)")

  def validateCustomer(id: Int, firstName: String, lastName: String): ValidatedNel[String, Customer] = {
    (
      validatePositiveInt(id).toValidatedNel[String].leftMap(_.map(error => s"id: $error")),
      validateTrimmedAndNonEmpty(firstName).toValidatedNel[String].leftMap(_.map(error => s"firstName: $error")),
      validateTrimmedAndNonEmpty(lastName).toValidatedNel[String].leftMap(_.map(error => s"lastName: $error"))
    ).mapN(Customer.apply)
  }
}

object ValidatedApp {
  def main(args: Array[String]): Unit = {
    val valid: Validated[Nothing, Int] = Validated.valid(1)
    Validated.invalid("error")
    println(ValidatedCustomer.validateCustomer(0, "", " Peter "))

    val list = (1 to 100).toList.traverse(i => if(i % 10 != 0) i.validNel[String] else s"error$i".invalidNel[Int])
    val sequence = list.sequence
    println(sequence)
  }
}
