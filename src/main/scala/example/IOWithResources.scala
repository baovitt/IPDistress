package ktedon.ipdistress

// SCALA STD imports
import scala.util.control.NonFatal

// CATS-Effect imports
import cats.effect.{IO, Outcome}

// Defines all IO methods.
object IOWithResource:

  // Takes a resource, a function on that resource, returns the
  // result of that function alongside the resource, and
  // safely closes the resource.
  def withResourcesAutoclose [T <: AutoCloseable, V] (
    resource: IO [T],
  ) (f: T => IO [V]): IO [V] = resource.bracketCase (f) { (source, outcome) =>
    outcome match
      case Outcome.Errored (e) => IO (closeAndAddSuppressed (source, e))
      case _                   => IO (closeAndAddSuppressed (source))
  }

  // Safely closes a resource with an exception.
  private inline def closeAndAddSuppressed (
    resource: AutoCloseable, e: Throwable
  ): Unit =
    try resource.close ()
    catch case NonFatal (suppressed) => e.addSuppressed (suppressed)

  private inline def closeAndAddSuppressed (resource: AutoCloseable): Unit =
    resource.close

end IOWithResource