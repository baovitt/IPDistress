package baovitt.ipdistress.jvm.logger

// JAVA imports
import java.time.Instant

// CATS imports
import cats.Eval

// CATS-EFFECT imports
import cats.effect.Sync

// FS2 imports
import fs2.Stream
import fs2.io
import fs2.text.utf8

// IPDISTRESS imports
import ktedon.ipdistress.logger.source.LoggerLocation

private final case class LogRecord(
    level: LogLevel,
    time: Instant,
    message: Eval[String],
    detail: Eval[Detail],
    location: LoggerLocation,
    ctx: LogContext,
    thrown: Option[Throwable]
) { self =>
    def toProvider[F[_]: Sync : LoggerProvider]: F[Unit] =
        Sync[F].handleErrorWith(
            LoggerProvider[F].log(level, ctx, time, message.value, detail.value.dump, location, thrown)
        ){err => 
            val error = Throwable(s"Unexpected error while logging record: $self", err).getStackTrace.toString
            Sync[F] delay (
                (Stream emit error through utf8.encode through io
                    .stdout [F]).compile.drain
            )
        }
}

