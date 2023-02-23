package baovitt.ipdistress.jvm

// CATS-EFFECT imports
import cats.effect.IO

sealed trait IPv4AddrError

case object InvalidOctetRange extends IPv4AddrError

final case class IPv4Addr private (
  octet1: Int, octet2: Int, octet3: Int, octet4: Int
):
  private def copy: Unit = ()

  override def toString: String = s"${octet1}.${octet2}.${octet3}.${octet4}"
end IPv4Addr

object IPv4Addr:
  private val rand = new scala.util.Random

  private def validOctetRange (octet: Int): Boolean = octet <= 255 && octet >= 1

  private def apply (octet1: Int, octet2: Int, octet3: Int,
  octet4: Int): Either [IPv4AddrError, IPv4Addr] =
    Either.cond (
      Seq (octet1, octet2, octet3, octet4) forall validOctetRange,
      new IPv4Addr (octet1, octet2, octet3, octet4), InvalidOctetRange
    )

  private def fromRandom: Either [IPv4AddrError, IPv4Addr] =
    val (octet1, octet2, octet3, octet4) = (
      rand.between (1, 255),
      rand.between (1, 255),
      rand.between (1, 255),
      rand.between (1, 255)
    )

    apply (octet1, octet2, octet3, octet4)

  val ipv4AddrStream: IO [Stream [IPv4Addr]] = IO (
    Stream.continually (fromRandom) collect { case Right (addr) => addr }
  )
end IPv4Addr