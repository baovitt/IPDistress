import Dependencies._

ThisBuild / scalaVersion     := "3.2.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "ipdistress",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      com.softwaremill.sttp.client3.`armeria-backend-cats`,
      com.softwaremill.sttp.client3.fs2,
      com.github.`xuwei-k`.`optparse-applicative`,
      "org.typelevel" %% "cats-collections-core" % "0.9.5"
    )
  )