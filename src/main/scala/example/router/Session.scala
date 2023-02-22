package ktedon.ipdistress.socks5.router

// JAVA imports
import java.time.Instant

case class Session (
    sessionId: String,
    key: List[Int],
    instant: Instant
)