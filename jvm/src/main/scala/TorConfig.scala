package baovitt.ipdistress.jvm

// CATS-EFFECT importss
import cats.effect.*

object TorConfig:
  def resource: Resource[IO, TorConfig] = Resource.make(create)(close)

  def create: IO[TorConfig] = IO { new TorConfig }

  def close(torConfig: TorConfig): IO[Unit] = IO { torConfig.close() }
end TorConfig

class TorConfig:
  def proxy: Proxy = ???

  def close(): Unit = ???
end TorConfig