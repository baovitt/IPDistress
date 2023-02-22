package ktedon.ipdistress.logger

// JAVA imports
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.logging.Logger

// CATS imports
import cats.collections.AvlMap

// CATS-EFFECT imports
import cats.effect.{Resource, Sync}

// FS2 imports
import fs2.Stream
import fs2.io
import fs2.text.utf8

// IPDISTRESS imports
import ktedon.ipdistress.logger.source.{File, Line, LoggerLocation}

object StandardProviders:

  /** simple log to console, that logs any events, where level <= maxLevel * */
  def console [F [_]: Sync] (maxLevel: LogLevel)
      : Resource [F, LoggerProvider [F]] = Resource pure
    new LoggerProvider [F]:

      val timeFormat = DateTimeFormatter.ISO_INSTANT

      def shouldLog (level: LogLevel, context: LogContext): Boolean =
        level.ordinal <= maxLevel.ordinal

      def log (
        level: LogLevel, 
        context: LogContext, 
        time: Instant,
        message: String,
        details: AvlMap [String, String], 
        location: LoggerLocation,
        thrown: Option [Throwable]
      ): F [Unit] =
        Sync [F] delay {
          (Stream emit {
            val errorLine =
              s"${timeFormat.format (time)} ${level} ${context.name} ${message} ${details} @ ${location.file.value}:${location.line.value}"
            val stackTrace = thrown.map (_.getStackTrace.toString) getOrElse ""
            s"$errorLine\n$stackTrace"
          } through utf8.encode through io.stdout [F]).compile.drain
        }

end StandardProviders
