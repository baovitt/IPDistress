package ktedon.ipdistress.endpoint

type Param = String
type ParamArg = String

trait QueryInputGen:
    def generate(param: String, length: Option[Int]): (Param, ParamArg)
end QueryInputGen

case object DefaultQueryInputGen extends QueryInputGen:
    def generate(param: String, len: Option[Int]): (Param, ParamArg) = 
        (param, "".padTo(len getOrElse 10, scala.util.Random.nextInt(9).toChar))
end DefaultQueryInputGen