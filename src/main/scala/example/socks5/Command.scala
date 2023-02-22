package ktedon.ipdistress.socks5

// CATS imports
import cats.Show

enum SOCKS5Command(val range: Int*):
    case Connect extends SOCKS5Command(0x01)
    case Bind extends SOCKS5Command(0x02)
    case UDPAssociate extends SOCKS5Command(0x03)
    case Unassigned extends SOCKS5Command((0x03 to 0xFF) :+ 0x00*)
end SOCKS5Command

object SOCKS5Command:

    def apply(method: Byte): SOCKS5Command =
        method.toInt match
            case 0x01 => SOCKS5Command.Connect
            case 0x02 => SOCKS5Command.Bind
            case 0x03 => SOCKS5Command.UDPAssociate
            case _ => SOCKS5Command.Unassigned

end SOCKS5Command

implicit val showSocks5Command: Show[SOCKS5Command] = Show.fromToString