package ir.irezaa.networkdispatcher.engine.okhttp

import ir.irezaa.networkdispatcher.core.NDNetworkResponseModel
import okhttp3.Response

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
interface OkHttpResponseBodyDecoder {
    fun <R : NDNetworkResponseModel> decode(okHttpResponse : Response, responseClass: Class<R>) : R?
}