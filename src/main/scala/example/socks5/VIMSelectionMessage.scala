package ktedon.ipdistress.socks5

// SCALA STD imports
import scala.collection.immutable.BitSet

sealed trait VIMSelectionMessageError
case object InvalidSOCKSVersion extends VIMSelectionMessageError
case object InvalidRequest extends VIMSelectionMessageError

final case class VIMSelectionMessage private (
  socksVersion: Int, methods: SOCKS5Method*
):
  private def copy: Unit = ()

  val bitSet: BitSet = BitSet(socksVersion +: methods.length +: methods.map(_.range.headOption getOrElse 0xFF)*)
end VIMSelectionMessage

object VIMSelectionMessage:
  private def apply (socksVersion: Int, methods: SOCKS5Method*)
      : Either [VIMSelectionMessageError, VIMSelectionMessage] =
    Either.cond (
      socksVersion == 5, new VIMSelectionMessage (socksVersion, methods*),
      InvalidSOCKSVersion
    )

  def fromMethods (
    socksVersion: Int, methods: SOCKS5Method*
  ): Either [VIMSelectionMessageError, VIMSelectionMessage] =
    apply (socksVersion, methods*)

  def fromBitset(msg: BitSet): Either [VIMSelectionMessageError, VIMSelectionMessage] =
    msg.toSeq match
      case Seq(version, _, methods*) => 
        apply (version, methods.map(method => SOCKS5Method(method.toByte))*)
      case _ => Left(InvalidRequest)

end VIMSelectionMessage
