package ktedon.ipdistress.socks5.router

// JAVA imports
import java.time.Instant

// CATS imports
import cats.Id

// CATS-EFFECT imports
import cats.effect.{IO, Sync}

// FS2 imports
import fs2.Stream

class Router(
    directoryAuthority: String = "http://localhost:9001",
    kind: RelayKind = RelayKind.Middle
)(sessions: Stream[Id, Session]):
    private inline def isActiveSession(session: Session): Boolean =
        session.instant.getEpochSecond() + 60 <= Instant.now().getEpochSecond()
 
    val activeSessionsStream: Stream[Id, Session] = sessions.filter(isActiveSession(_))



end Router