package ktedon.ipdistress.socks5

// CATS imports
import cats.Show

enum SOCKS5Method(val range: Int*):
    case NoAuth extends SOCKS5Method(0x00)
    case GSSAPI extends SOCKS5Method(0x01)
    case UsernamePwd extends SOCKS5Method(0x02)
    case IanaAssigned extends SOCKS5Method(0x03 to 0x7F*)
    case Reserved extends SOCKS5Method(0x80 to 0xFE*)
    case NoMethods extends SOCKS5Method(0xFF)
end SOCKS5Method

object SOCKS5Method:

    def apply(method: Byte): SOCKS5Method =
        method.toInt match
            case 0x00 => SOCKS5Method.NoAuth
            case 0x01 => SOCKS5Method.GSSAPI
            case 0x02 => SOCKS5Method.UsernamePwd
            case 0x03 => SOCKS5Method.IanaAssigned
            case 0xFF => SOCKS5Method.NoMethods
            case _ => SOCKS5Method.Reserved

end SOCKS5Method

implicit val showSocks5Method: Show[SOCKS5Method] = Show.fromToString