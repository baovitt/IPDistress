package baovitt.ipdistress.jvm.logger

// CATS imports
import cats.Show
import cats.collections.AvlMap

// IPDISTRESS imports
import ktedon.ipdistress.logger.source.Text

class Detail(data: AvlMap[String, String]):
    import Detail.*

    def and[A: Show](a: Text[A]): Detail =  
        mk(data + (a.source -> Show[A].show(a.value)))

    def and[A: Show](k: String, a: A): Detail =
        mk(data + (k -> Show[A].show(a)))

    def append(detail: Detail): Detail =
        mk(data ++ detail.dump)

    def dump = data
end Detail

object Detail:
    def empty: Detail = mk(AvlMap.empty)

    def apply[A : Show](a: Text[A]): Detail =
        mk(AvlMap(a.source -> Show[A].show(a.value)))

    def as[A: Show](k: String, a: A): Detail =
        mk(AvlMap(k -> Show[A].show(a)))

    private def mk(data: AvlMap[String, String]): Detail = new Detail(data)

end Detail