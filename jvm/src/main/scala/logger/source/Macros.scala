package baovitt.ipdistress.jvm.logger.source

// SCALA STD imports:
import scala.language.implicitConversions
import scala.quoted._

object Macros:
  def fileImpl(using Quotes): Expr[File] =
    import quotes.reflect._
    val file = quotes.reflect.Position.ofMacroExpansion.sourceFile.jpath.toAbsolutePath.toString
    '{ File(${Expr(file)}) }

  def lineImpl(using Quotes): Expr[Line] =
    val line = quotes.reflect.Position.ofMacroExpansion.startLine + 1
    '{ Line(${Expr(line)}) }

  def text[T: Type](v: Expr[T])(using Quotes): Expr[Text[T]] = 
    import quotes.reflect._
    '{ Text[T]($v, ${Expr(v.asTerm.pos.sourceCode.get)}) }
end Macros
