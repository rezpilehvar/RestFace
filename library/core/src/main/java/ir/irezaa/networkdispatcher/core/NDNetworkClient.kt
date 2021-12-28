package ir.irezaa.networkdispatcher.core

/*
   Creation Time: 12/28/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class NDNetworkClient private constructor(
    private val networkEngine: NDNetworkEngine,
    private val defaultHeaders: Map<String, String>?
) : NDNetworkRequestDispatcher {

    override fun <R : NDNetworkResponseModel> execute(request: NDNetworkRequest<R>): R {
        defaultHeaders?.let {
            request.addHeader(defaultHeaders)
        }
        return networkEngine.execute(request)
    }


    class Builder(private val networkEngine: NDNetworkEngine) {
        private var headers: Map<String, String>? = null

        fun defaultHeaders(headers: Map<String, String>) {
            this.headers = headers
        }

        fun build(): NDNetworkClient {
            return NDNetworkClient(networkEngine, headers)
        }
    }
}