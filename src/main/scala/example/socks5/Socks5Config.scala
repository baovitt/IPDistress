package ktedon.ipdistress.socks5

sealed trait SOCKS5ConfigError
case object BadLength extends SOCKS5ConfigError
case object InvalidCharacter extends SOCKS5ConfigError

final case class SOCKS5Config private (value: String):
    private def copy: Unit = ()
end SOCKS5Config

object SOCKS5Config:
    private def apply(value: String): Either[SOCKS5ConfigError, SOCKS5Config] =
        Either.cond(
            value.length == 12,
            new SOCKS5Config(value),
            BadLength
        )

    def fromString(value: String): Either[SOCKS5ConfigError, SOCKS5Config] = apply(value)
end SOCKS5Config