package ktedon.ipdistress.socks5

// CATS imports
import cats.Show

enum SOCKS5AddressType(val values: Int*):
    case IPv4 extends SOCKS5AddressType(0x01)
    case DomainName extends SOCKS5AddressType(0x03)
    case IPv6 extends SOCKS5AddressType(0x04)
    case Unassigned extends SOCKS5AddressType((0x05 to 0xFF) :+ 0x02 :+ 0x00*)
end SOCKS5AddressType

object SOCKS5AddressType:

    def apply(method: Byte): SOCKS5AddressType =
        method.toInt match
            case 0x01 => SOCKS5AddressType.IPv4
            case 0x03 => SOCKS5AddressType.DomainName
            case 0x04 => SOCKS5AddressType.IPv6
            case _ => SOCKS5AddressType.Unassigned

end SOCKS5AddressType

implicit val showSocks5AddressType: Show[SOCKS5AddressType] = Show.fromToString