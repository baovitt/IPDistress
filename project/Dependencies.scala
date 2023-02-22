import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11"

  val `armeria-backend-cats-version` = "3.8.8"
  val `fs2-version` = "3.8.8"

  val `optparse-applicative-version` = "0.9.2"

  object com {
    object softwaremill {
      object sttp {
        object client3 {
          val `armeria-backend-cats` = "com.softwaremill.sttp.client3" %% "armeria-backend-cats" % `armeria-backend-cats-version`
          val fs2 = "com.softwaremill.sttp.client3" %% "fs2" % `fs2-version`
        }
      }
    }
    object github {
      object `xuwei-k` {
        val `optparse-applicative` = "com.github.xuwei-k" %% "optparse-applicative" % `optparse-applicative-version`
      }
    }
  }
}
