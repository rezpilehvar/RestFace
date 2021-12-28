package ir.irezaa.networkdispatcher.core


/*
   Creation Time: 12/28/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
interface NDNetworkRequestDispatcher {
    fun <R : NDNetworkResponseModel> execute(request : NDNetworkRequest<R>) : R
}