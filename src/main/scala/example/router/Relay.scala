package ktedon.ipdistress.socks5.router

case class Relay(
    name: String,
    relayKind: RelayKind,
    session: Option[Session]
)

