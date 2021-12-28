package ir.irezaa.networkdispatcher.engine.okhttp

import ir.irezaa.networkdispatcher.core.NDNetworkResponseModel
import ir.irezaa.networkdispatcher.core.NDResponseDecoder
import okhttp3.Response

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class NDOkHttpResponseBodyDecoder(private val NDResponseDecoder: NDResponseDecoder) : OkHttpResponseBodyDecoder {
    override fun <R : NDNetworkResponseModel> decode(
        okHttpResponse: Response,
        responseClass: Class<R>
    ): R? {
        if (okHttpResponse.isSuccessful) {
            val dataString = okHttpResponse.body?.string()

            dataString?.let {
                return NDResponseDecoder.decode(dataString,responseClass)
            }

            return null
        }

        return null
    }
}