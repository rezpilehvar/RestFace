package ir.irezaa.networkdispatcher.engine.okhttp

import ir.irezaa.networkdispatcher.core.NDNetworkEngine
import ir.irezaa.networkdispatcher.core.NDNetworkResponseModel
import ir.irezaa.networkdispatcher.core.NDNetworkRequest
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.IllegalArgumentException
import java.net.URL

/*
   Creation Time: 12/28/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class OkHttpNetworkEngine(
    private val okHttpClient: OkHttpClient,
    private val networkRequestEncoder: OkHttpRequestBodyEncoder,
    private val networkResponseDecoder : OkHttpResponseBodyDecoder
) : NDNetworkEngine {

    override fun <R : NDNetworkResponseModel> execute(request: NDNetworkRequest<R>): R {
        val okHttpRequestBuilder = Request.Builder()

        request.headers.forEach {
            okHttpRequestBuilder.addHeader(it.key, it.value)
        }

        val okHttpURL = URL(request.url).toHttpUrlOrNull()
            ?: throw IllegalArgumentException("url is not valid!")

        when (request) {
            is NDNetworkRequest.GET -> {
                okHttpRequestBuilder.get()

                request.queryParameters.forEach {
                    okHttpURL.newBuilder()
                        .addQueryParameter(it.key,it.value)
                }
            }
            is NDNetworkRequest.POST -> {
                val mediaType = request.NDMediaType.rawValue.toMediaType()
                val requestBody = networkRequestEncoder.encode(request.body,mediaType)

                okHttpRequestBuilder.post(requestBody)
            }
        }

        okHttpRequestBuilder.url(okHttpURL)

        val okHttpResponse = okHttpClient.newCall(okHttpRequestBuilder.build()).execute()

        return networkResponseDecoder.decode(okHttpResponse,request.responseClass)!!
    }
}