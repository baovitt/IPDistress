package ktedon.ipdistress

// SCALA STD imports:
import scala.language.implicitConversions
import scala.quoted.*

// CATS imports
import cats.syntax.show.toShow

// CATS-EFFECT imports
import cats.effect.{IO, IOApp, ExitCode}

// FS2 imports
import fs2.*
import fs2.text.utf8

// SOCKJET imports
import ktedon.ipdistress.socks5.*

// LOGGER imports
import ktedon.ipdistress.logger.source.*

// inline def to(expr: Expr[SourceLocation[String]]): SourceLocation[String] =
//   ${expr}

object IPDistress extends IOApp:

  def run (args: List [String]): IO [ExitCode] = for {
    userAgentStream <- UserAgent.userAgentStream
    requestRateStream <- RequestRate.requestRateStream (5000, 1.4)
    ipv4AddrStream <- IPv4Addr.ipv4AddrStream
    _ <- (Stream emit userAgentStream.take(10).map(_ + '\n').mkString through utf8.encode through io
      .stdout [IO]).compile.drain
  } yield ExitCode.Success

end IPDistress
