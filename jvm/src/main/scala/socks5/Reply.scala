package baovitt.ipdistress.jvm.socks5

// CATS imports
import cats.Show

enum Reply(val range: Int*):
    case Succeeded extends Reply(0x00)
    case GeneralSocksServerFailure extends Reply(0x01)
    case ConnectionNotAllowed extends Reply(0x02)
    case NetworkUnreachable extends Reply(0x03)
    case HostUnreachable extends Reply(0x04)
    case ConnectionRefused extends Reply(0x05)
    case TTLExpired extends Reply(0x06)
    case CommandNotSupported extends Reply(0x07)
    case AddressTypeNotSupported extends Reply(0x08)
    case Unassigned extends Reply(0x09 to 0xFF*)
end Reply

object Reply:
    def apply(method: Byte): Reply =
        method.toInt match
            case 0x00 => Reply.Succeeded
            case 0x01 => Reply.GeneralSocksServerFailure
            case 0x02 => Reply.ConnectionNotAllowed
            case 0x03 => Reply.NetworkUnreachable
            case 0x04 => Reply.HostUnreachable
            case 0x05 => Reply.ConnectionRefused
            case 0x06 => Reply.TTLExpired
            case 0x07 => Reply.CommandNotSupported
            case 0x08 => Reply.AddressTypeNotSupported
            case _ => Reply.Unassigned
end Reply

implicit val showSocks5Reply: Show[Reply] = Show.fromToString