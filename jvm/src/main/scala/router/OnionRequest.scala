package baovitt.ipdistress.jvm.router

case class OnionRequest (
    sessionId: String,
    payload: List[Int]
)