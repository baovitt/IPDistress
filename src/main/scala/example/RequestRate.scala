package ktedon.ipdistress

// SCALA STD imports
import scala.concurrent.duration.FiniteDuration

// JAVA STD imports
import java.util.concurrent.TimeUnit

// CATS-EFFECT imports
import cats.effect.IO

object RequestRate:
  type Milliseconds = Long
  type Variance = Double

  private def getStochasticDuration (
    median: Long,
    variance: Double
  ): FiniteDuration =
    FiniteDuration ((median + scala.util.Random
      .nextGaussian () * variance * 1000).toLong, TimeUnit.MILLISECONDS)

  val requestRateStream
      : (Milliseconds, Variance) => IO [Stream [FiniteDuration]] =
    (ms, variance) =>
      IO (Stream
        .continually (getStochasticDuration (ms, variance))
        .filter (_.length > 10))
end RequestRate
