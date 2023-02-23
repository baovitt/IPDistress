package baovitt.ipdistress.jvm.socks5

sealed trait SOCKSRequestError
case object InvalidSOCKSRequestVersion extends SOCKSRequestError

final case class SOCKSRequest private (
  socksVersion: Int, cmd: SOCKS5Command, addrType: SOCKS5AddressType,
  destAddress: String, port: Int
):

  private def copy: Unit = ()

end SOCKSRequest

object SOCKSRequest:

  private def apply (
    socksVersion: Int, cmd: SOCKS5Command, addrType: SOCKS5AddressType,
    destAddress: String, port: Int
  ): Either [SOCKSRequestError, SOCKSRequest] =
    Either.cond (
      socksVersion == 5,
      new SOCKSRequest (socksVersion, cmd, addrType, destAddress, port),
      InvalidSOCKSRequestVersion
    )

  def fromRequest (
    socksVersion: Int, cmd: SOCKS5Command, addrType: SOCKS5AddressType,
    destAddress: String, port: Int
  ): Either [SOCKSRequestError, SOCKSRequest] =
    apply (socksVersion, cmd, addrType, destAddress, port)

end SOCKSRequest