package ktedon.ipdistress.logger

// JAVA imports
import java.time.Instant

// CATS imports
import cats.collections.AvlMap

// IPDISTRESS imports
import ktedon.ipdistress.logger.source.LoggerLocation

trait LoggerProvider[F[*]]:
    def shouldLog(level: LogLevel, context: LogContext): Boolean

    def log(
        level: LogLevel,
        context: LogContext,
        time: Instant,
        message: String,
        details: AvlMap[String, String],
        location: LoggerLocation,
        thrown: Option[Throwable] = None
    ): F[Unit]
end LoggerProvider

object LoggerProvider:
    inline def apply[F[_]](implicit instance: LoggerProvider[F]): LoggerProvider[F] = instance
end LoggerProvider