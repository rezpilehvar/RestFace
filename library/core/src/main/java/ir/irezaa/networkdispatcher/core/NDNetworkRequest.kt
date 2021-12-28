package ir.irezaa.networkdispatcher.core

/*
   Creation Time: 12/28/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
sealed class NDNetworkRequest<Response : NDNetworkResponseModel>(
    val responseClass: Class<Response>,
    val url : String
) {

    private val _headers = mutableMapOf<String, String>()
    val headers : Map<String,String>
        get() {
            return _headers
        }

    fun addHeader(key: String, value: String) {
        this._headers[key] = value
    }

    fun addHeader(headers : Map<String,String>) {
        this._headers.putAll(headers)
    }

    class GET<Response : NDNetworkResponseModel>private constructor(url : String, responseClass: Class<Response>) :
        NDNetworkRequest<Response>(responseClass,url) {

        private val _queryParameters = mutableMapOf<String, String>()

        val queryParameters : Map<String,String>
        get() {
            return _queryParameters
        }

        class Builder<Response : NDNetworkResponseModel>(url : String, responseClass: Class<Response>) :
            RequestBuilder<Builder<Response>, Response> {
            private val request = GET(url,responseClass)

            fun addParameter(key: String, value: String): Builder<Response> {
                request._queryParameters[key] = value
                return this
            }

            override fun addHeader(key: String, value: String): Builder<Response> {
                request.addHeader(key,value)
                return this
            }

            override fun build(): NDNetworkRequest<Response> {
                return request
            }
        }
    }

    class POST<Response : NDNetworkResponseModel>private constructor(url : String, responseClass: Class<Response>) :
        NDNetworkRequest<Response>(responseClass,url) {

        var NDMediaType : NDBodyMediaType = NDBodyMediaType.JSON
        var body: Any? = null

        class Builder<Response : NDNetworkResponseModel>(url : String, responseClass: Class<Response>) :
            RequestBuilder<Builder<Response>, Response> {
            private val request = POST(url,responseClass)

            override fun addHeader(key: String, value: String): Builder<Response> {
                request.addHeader(key,value)
                return this
            }

            fun <T : NDNetworkRequestModel> setBody(body: T): Builder<Response> {
                request.body = body
                request.NDMediaType = NDBodyMediaType.JSON
                return this
            }

            override fun build(): NDNetworkRequest<Response> {
                return request
            }
        }
    }


    internal interface RequestBuilder<B,R : NDNetworkResponseModel> {
        fun addHeader(key: String, value: String): B
        fun build(): NDNetworkRequest<R>
    }
}
