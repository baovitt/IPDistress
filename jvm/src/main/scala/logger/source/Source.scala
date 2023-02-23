package baovitt.ipdistress.jvm.logger.source

// SCALA STD imports:
import scala.quoted._

abstract class SourceValue[T]:
  def value: T
end SourceValue

abstract class SourceCompanion[T, V <: SourceValue[T]](build: T => V):
  def apply()(implicit s: V): T = s.value
  implicit def wrap(s: T): V = build(s)
end SourceCompanion

case class File(value: String) extends SourceValue[String]
object File extends SourceCompanion[String, File](File(_)):
    inline implicit def file: File = ${ Macros.fileImpl }
end File

case class Line(value: Int) extends SourceValue[Int]
object Line extends SourceCompanion[Int, Line](Line(_)):
    inline implicit def line: Line = ${ Macros.lineImpl }
end Line

case class LoggerLocation(file: File, line: Line)

case class Text[T](value: T, source: String)
object Text:
  inline implicit def generate[T](v: => T): Text[T] = ${ Macros.text('v) }
  inline def apply[T](v: => T): Text[T] = ${ Macros.text('v) }
end Text