package ir.irezaa.networkdispatcher.converter.gson

import ir.irezaa.networkdispatcher.core.NDRequestEncoder
import ir.irezaa.networkdispatcher.core.NDResponseDecoder
import com.google.gson.Gson

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class NDGsonDataConverter(private val gson : Gson) : NDResponseDecoder , NDRequestEncoder {
    override fun encode(body: Any?): String? {
        if (body == null) {
            return null
        }

        val jsonData = gson.toJson(body)

        return jsonData
    }

    override fun <DecodeType> decode(data: String, decodeClass: Class<DecodeType>): DecodeType? {
        return gson.fromJson(data,decodeClass)
    }
}