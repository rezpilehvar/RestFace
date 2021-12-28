package ir.irezaa.networkdispatcher.engine.cronet

import ir.irezaa.networkdispatcher.core.NDNetworkEngine
import ir.irezaa.networkdispatcher.core.NDNetworkRequest
import ir.irezaa.networkdispatcher.core.NDNetworkResponseModel
import ir.irezaa.networkdispatcher.core.NDResponseDecoder
import org.chromium.net.CronetEngine
import java.util.concurrent.Executors


/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class NDCronetEngine(
    private val cronetEngine: CronetEngine,
    private val NDResponseDecoder: NDResponseDecoder
) : NDNetworkEngine {

    override fun <R : NDNetworkResponseModel> execute(request: NDNetworkRequest<R>): R {
        val callBack = CronetRequestCallback()

        val cronetRequestBuilder = cronetEngine
            .newUrlRequestBuilder(
                request.url,
                callBack,
                Executors.newFixedThreadPool(1)
            )

        request.headers.forEach {
            cronetRequestBuilder.addHeader(it.key, it.value)
        }

        cronetRequestBuilder.build().start()

        val responseString = callBack.waitForDone()

        return NDResponseDecoder.decode(responseString!!, request.responseClass)!!
    }
}