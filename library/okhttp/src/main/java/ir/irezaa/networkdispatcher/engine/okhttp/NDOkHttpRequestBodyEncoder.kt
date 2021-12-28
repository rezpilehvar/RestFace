package ir.irezaa.networkdispatcher.engine.okhttp

import ir.irezaa.networkdispatcher.core.NDRequestEncoder
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class NDOkHttpRequestBodyEncoder(private val NDRequestEncoder: NDRequestEncoder) :
    OkHttpRequestBodyEncoder {
    override fun encode(body: Any?, mediaType: MediaType): RequestBody {
        if (body == null) {
            return "{}".toRequestBody(mediaType)
        }

        val encodedResponse = NDRequestEncoder.encode(body)

        encodedResponse?.let {
            return it.toRequestBody(mediaType)
        }

        return "{}".toRequestBody(mediaType)
    }
}