package ir.irezaa.networkdispatcher.core

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
enum class NDBodyMediaType(val rawValue: String) {
    JSON("application/json; charset=utf-8"),
    MultiPart("multipart/form-data; charset=utf-8")
}