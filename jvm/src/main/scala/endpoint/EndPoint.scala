package baovitt.ipdistress.jvm.endpoint

class EndpointFactory(uri: String, queryParams: QueryParam*):
    val paramsMap = queryParams.map(_.genTuple).toMap
    val _ = queryParams

    inline def genQueries = 
        paramsMap.map((param, arg) => s"${param}=${arg}")
end EndpointFactory