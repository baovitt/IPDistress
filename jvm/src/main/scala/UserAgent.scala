package baovitt.ipdistress.jvm

// SCALA STD imports
import scala.io.Source
import scala.util.Random

// CATS-EFFECT imports
import cats.effect.IO

object UserAgent:

  private val userAgentResource =
    IO (scala.io.Source.fromFile ("useragents.txt"))

  private val userAgents =
    IOWithResource.withResourcesAutoclose (userAgentResource) { src =>
      IO (src.getLines.toList)
    }

  val userAgentStream = userAgents.map { agents =>
    Stream
      .continually {
        agents match
          case Nil    => None
          case agents => agents.lift (Random.nextInt (agents.size))
      }
      .collect { case Some (agent) => agent }
  }

end UserAgent

