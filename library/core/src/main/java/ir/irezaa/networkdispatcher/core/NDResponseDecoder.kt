package ir.irezaa.networkdispatcher.core

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
interface NDResponseDecoder {
    fun <DecodeType> decode(data : String, decodeClass : Class<DecodeType>) : DecodeType?
}