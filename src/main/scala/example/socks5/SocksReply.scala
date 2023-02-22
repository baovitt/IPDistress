package ktedon.ipdistress.socks5

sealed trait SOCKSReplyError
case object InvalidSOCKSReplyVersion extends SOCKSReplyError

final case class SOCKSReply private (
  socksVersion: Int, cmd: SOCKS5Command, addrType: SOCKS5AddressType,
  destAddress: String, port: Int
):

  private def copy: Unit = ()

end SOCKSReply

object SOCKSReply:

  private def apply (
    socksVersion: Int, cmd: SOCKS5Command, addrType: SOCKS5AddressType,
    destAddress: String, port: Int
  ): Either [SOCKSReplyError, SOCKSReply] =
    Either.cond (
      socksVersion == 5,
      new SOCKSReply (socksVersion, cmd, addrType, destAddress, port),
      InvalidSOCKSReplyVersion
    )

  def fromRequest (
    socksVersion: Int, cmd: SOCKS5Command, addrType: SOCKS5AddressType,
    destAddress: String, port: Int
  ): Either [SOCKSReplyError, SOCKSReply] =
    apply (socksVersion, cmd, addrType, destAddress, port)

end SOCKSReply