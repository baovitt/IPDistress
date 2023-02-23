package baovitt.ipdistress.jvm.logger

import ktedon.ipdistress.logger.source.*

trait LogContext:
  def name: String
end LogContext

object LogContext:
  implicit def defaultInstance (implicit logLocation: LoggerLocation)
      : LogContext =
    new LogContext:
      def name: String = s"${logLocation.file.value}:${logLocation.line.value}"
end LogContext
