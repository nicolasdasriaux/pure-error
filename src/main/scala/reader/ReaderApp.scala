package reader

import service._

object ReaderApp {
  def main(args: Array[String]): Unit = {
    val program: Reader[GlobalConfig, (Customer, Item)] = for {
      customerService <- Reader(CustomerService.apply).local[GlobalConfig](_.customerServiceConfig)
      itemService <- Reader(ItemService.apply).local[GlobalConfig](_.itemServiceConfig)
      customer = customerService.find(1)
      item = itemService.find(1)
    } yield (customer, item)

    val globalConfig = GlobalConfig(
      customerServiceConfig = CustomerServiceConfig(
        firstNamePrefix = "First Name",
        lastNamePrefix = "Last Name"
      ),
      itemServiceConfig = ItemServiceConfig(
        namePrefix = "Name"
      )
    )

    println(program.run(globalConfig))
  }
}
