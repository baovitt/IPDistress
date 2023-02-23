package baovitt.ipdistress.jvm

// OPTPARSE imports
import optparse_applicative._

// CATS imports
import cats.syntax.all.*

object CommandParser:
    // case class CommandOps(
    //     medianRequestRate: Long, // In milliseconds
    //     requestRateVariance: Double, // Calculated on a gaussian distribution.
    //     setAdvancedUserAgents: Boolean, // If true, IPDistress modifies user agents with each request.
    //     setTorProxy: Boolean, // If true, IPDistress performs request through the tor network using SOCKS5.
    //     sendKeepalive: Boolean, // If true, IPDistress sends Keep-alive requests to keep the connection open.
    //     noCache: Boolean, // If true, IPDistress attempts to disable server caching with a no-cache url prefix.
    //     urlTransformation: Boolean, // If true, IPDistress applies randomized url query parameter alterations.
    //     smartFeatureAlteration: Boolean, // If true, IPDistress will stop using some feature is the target beings to decline request. 
    //     browserEmulation: Boolean,
    //     urlList: String, // Filename of all urls to be attacked.
    //     logFile: Option[String]
    // )

    // val parseOpts: Parser[CommandOps] = ^^^(
    //     long(short("mrr"), long("median-request-rate"))
    // )

    // case class Opts(verbose: Boolean, name: String, inputs: NonEmptyList[File], output: Option[File])

    // val parseOpts: Parser[Opts] = ^^^(
    //   switch(short('v'), long("verbose")),
    //   strOption(short('n'), long("name")) <|> pure("<default name>"),
    //   some(strArgument(metavar("FILE"), help("Files to read")).map(new File(_))),
    //   optional(strOption(short('f'), long("file"), metavar("FILE")).map(new File(_)))
    // )(Opts.apply)


end CommandParser