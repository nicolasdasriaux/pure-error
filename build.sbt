addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")

lazy val root = (project in file("."))
  .settings(
    name := "handling-errors",
    version := "0.1",
    scalaVersion := "2.12.8",

    scalacOptions += "-Ypartial-unification",
    resolvers += Resolver.sonatypeRepo("releases"),

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.6.1",
      "org.typelevel" %% "cats-effect" % "1.3.1",

      "dev.zio" %% "zio" % "1.0.0-RC11-1",
      "dev.zio" %% "zio-streams" % "1.0.0-RC11-1"
    )
  )
