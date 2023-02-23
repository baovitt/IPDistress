package baovitt.ipdistress.jvm.endpoint

sealed trait QueryParamError

case object EmptyParameterName extends QueryParamError

final case class QueryParam private (param: String, gen: QueryInputGen):
    private def copy: Unit = ()

    def genTuple: (String, String) = gen.generate(param, None)
end QueryParam

object QueryParam:

    private def apply(param: String, gen: QueryInputGen): Either[QueryParamError, QueryParam] =
        Either.cond(
            param.length > 0,
            new QueryParam(param, gen),
            EmptyParameterName
        )

    def fromGen(param: String, gen: QueryInputGen): Either[QueryParamError, QueryParam] =
        apply(param, gen)

    implicit def fromDefaultGen(param: String): Either[QueryParamError, QueryParam] = 
        apply(param, DefaultQueryInputGen)
end QueryParam
