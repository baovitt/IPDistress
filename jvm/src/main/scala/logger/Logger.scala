package baovitt.ipdistress.jvm.logger

// JAVA imports
import java.time.Instant

// CATS imports
import cats.implicits._
import cats.{Applicative, Eval, Show}

// CATS-EFFECT imports
import cats.effect.{Concurrent, Resource, Sync, Deferred}

// FS2 imports
import fs2.Chunk.Queue
import fs2.*

// IPDISTRESS imports
import ktedon.ipdistress.logger.source.{File, Line, LoggerLocation}

trait Logger[F[*]]:
    def log(level: LogLevel, message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit]

    inline def error(message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        log(LogLevel.Error, message, detail, thrown)

    inline def warn(message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        log(LogLevel.Warn, message, detail, thrown)

    inline def info(message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        log(LogLevel.Info, message, detail, thrown)

    inline def config(message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        log(LogLevel.Config, message, detail, thrown)

    inline def debug(message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        log(LogLevel.Debug, message, detail, thrown)

    inline def trace(message: => String, detail: => Detail = Detail.empty, thrown: Option[Throwable] = None)(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        log(LogLevel.Trace, message, detail, thrown)

    def observe[A : Show](level: LogLevel, message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A]

    inline def observeAsError[A: Show](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observe(LogLevel.Error, message, detail)(f)

    inline def observeAsWarn[A: Show](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observe(LogLevel.Warn, message, detail)(f)

    inline def observeAsInfo[A: Show](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observe(LogLevel.Info, message, detail)(f)

    inline def observeAsConfig[A: Show](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observe(LogLevel.Config, message, detail)(f)

    inline def observeAsDebug[A: Show](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observe(LogLevel.Debug, message, detail)(f)

    inline def observeAsTrace[A: Show](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observe(LogLevel.Trace, message, detail)(f)

    def observeRaised[A](level: LogLevel, message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A]

    inline def observeRaisedAsError[A](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observeRaised(LogLevel.Error, message, detail)(f)

    inline def observeRaisedAsWarn[A](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observeRaised(LogLevel.Warn, message, detail)(f)

    inline def observeRaisedAsInfo[A](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observeRaised(LogLevel.Info, message, detail)(f)

    inline def observeRaisedAsConfig[A](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observeRaised(LogLevel.Config, message, detail)(f)

    inline def observeRaisedAsDebug[A](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observeRaised(LogLevel.Debug, message, detail)(f)

    inline def observeRaisedAsTrace[A](message: => String, detail: => Detail = Detail.empty)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        observeRaised(LogLevel.Trace, message, detail)(f)
end Logger

object Logger:
    inline def apply[F[_]](implicit instance: Logger[F]): Logger[F] = instance

    // def sync[F[_] : Sync : LoggerProvider]: Resource[F, Logger[F]] = {
    //     Resource.pure {
    //         new CommonLog[F]:
    //             inline def log_(level: LogLevel, message: => String, detail: => Detail, thrown: Option[Throwable])(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
    //                 LogRecord(level, Instant.now, Eval.later(message), Eval.later(detail), LoggerLocation(file, line), ctx, thrown).toProvider
    //     }
    // }

    private abstract class CommonLog[F[_] : Sync : LoggerProvider] extends Logger[F] {
        inline def log_(level: LogLevel, message: => String, detail: => Detail, thrown: Option[Throwable])(implicit line: Line, file: File, ctx: LogContext): F[Unit]

        // def log(level: LogLevel, message: => String, detail: => Detail, thrown: Option[Throwable])(implicit line: Line, file: File, ctx: LogContext): F[Unit] =
        //     Sync[F].suspend {
        //         if (! LoggerProvider[F].shouldLog(level, ctx)) Applicative[F].unit
        //         else log_(level, message, detail, thrown)
        //     }

        // def observe[A: Show](level: LogLevel, message: => String, detail: => Detail)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        //     Sync[F].suspend {
        //         if (!LoggerProvider[F].shouldLog(level, ctx)) f
        //         else f.flatTap { a => log_(level, message, detail append Detail.as("value", a), None) }
        //     }

        // def observeRaised[A](level: LogLevel, message: => String, detail: => Detail)(f: F[A])(implicit line: Line, file: File, ctx: LogContext): F[A] =
        //     Sync[F].suspend {
        //         if (!LoggerProvider[F].shouldLog(level, ctx)) f
        //         else f.handleErrorWith { thrown =>
        //         log_(level, message, detail, Some(thrown)) *>
        //             Sync[F].raiseError(thrown)
        //         }
        //     }
    }
end Logger