package baovitt.ipdistress.jvm.router


// JAVA imports
import java.time.Instant

case class Session (
    sessionId: String,
    key: List[Int],
    instant: Instant
)