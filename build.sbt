import Dependencies._

ThisBuild / scalaVersion     := "3.2.1"
ThisBuild / organization     := "com.baovitt"
ThisBuild / organizationName := "baovitt"

lazy val root = (project in file("."))
  .aggregate(ipdistress.js, ipdistress.jvm)
  .settings(
    name := "ipdistress",
  )

lazy val ipdistress = crossProject(JSPlatform, JVMPlatform).in(file("."))
  // .withSuffixFor(JSPlatform)
  .settings(
    name := "ipdistress",
    version := "0.1-SNAPSHOT",
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      scalaTest % Test,
      com.softwaremill.sttp.client3.`armeria-backend-cats`,
      com.softwaremill.sttp.client3.fs2,
      com.github.`xuwei-k`.`optparse-applicative`,
      "org.typelevel" %% "cats-collections-core" % "0.9.5"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "be.doeraene" %%% "web-components-ui5" % "1.9.0",
      "com.raquo" %%% "laminar" % "0.14.5",
      "org.scala-js" %%% "scalajs-dom" % "2.1.0"
    ),
    npmDependencies in Compile ++= Seq(
      "@ui5/webcomponents" -> "1.9.1",
      "@ui5/webcomponents-fiori" -> "1.9.1",
    ),
    scalaJSUseMainModuleInitializer := true
  )
  .jsConfigure { project => project.enablePlugins(ScalaJSBundlerPlugin)}

lazy val ipJS     = ipdistress.js
lazy val ipJVM    = ipdistress.jvm