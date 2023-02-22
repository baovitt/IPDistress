package ktedon.ipdistress.socks5.router

case class OnionRequest (
    sessionId: String,
    payload: List[Int]
)