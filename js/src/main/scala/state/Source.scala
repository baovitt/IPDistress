package baovitt.ipdistress.js.state

sealed trait SourceError
case object InvalidSourceNameLength extends SourceError

final case class Source private (name: String):
    private def copy: Unit = ()
end Source

object Source:
    private def apply(
        name: String
    ): Either[SourceError, Source] = for {
        name <- Either.cond(name.length > 0, name, InvalidSourceNameLength)
    } yield new Source(name)

    def fromString(
        name: String
    ): Either[SourceError, Source] = apply(name)
end Source