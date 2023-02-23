package baovitt.ipdistress.jvm.router

case class Relay(
    name: String,
    relayKind: RelayKind,
    session: Option[Session]
)

