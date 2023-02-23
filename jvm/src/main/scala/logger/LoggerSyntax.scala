package baovitt.ipdistress.jvm.logger

// CATS imports
import cats.implicits._
import cats.{Applicative, Show}

// FS2 imports
import fs2.Stream

// IPDISTRESS imports
import ktedon.ipdistress.logger.source.{File, Line}

object LoggerSyntax:
    class StreamLogOps[F[_] : Applicative : Logger, A : Show](val stream: Stream[F, A]):
        def apply(level: LogLevel, message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext): Stream[F, A] =
            lazy val m1 = message
            lazy val d1 = detail
            stream.evalMap( a =>
                Logger[F].log(level, m1, Detail.as("value", a) append d1) as a
            )

        inline def error(message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext) =
            apply(LogLevel.Error, message, detail)

        inline def warn(message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext) =
            apply(LogLevel.Warn, message, detail)

        inline def info(message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext) =
            apply(LogLevel.Info, message, detail)

        inline def config(message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext) =
            apply(LogLevel.Config, message, detail)

        inline def debug(message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext) =
            apply(LogLevel.Debug, message, detail)

        inline def trace(message: => String, detail: => Detail = Detail.empty)(implicit line: Line, file: File, ctx: LogContext) =
            apply(LogLevel.Trace, message, detail)
    end StreamLogOps

    implicit class StreamLog[F[_] : Applicative:  Logger, A : Show](val stream: Stream[F, A]):
        def log: StreamLogOps[F, A] = new StreamLogOps[F, A](stream)
    end StreamLog
end LoggerSyntax