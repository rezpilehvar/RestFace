package ir.irezaa.networkdispatcher.engine.okhttp

import okhttp3.MediaType
import okhttp3.RequestBody

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
interface OkHttpRequestBodyEncoder {
    fun encode(body : Any?, mediaType: MediaType) : RequestBody
}