package ir.irezaa.networkdispatcher.engine.cronet

import android.os.ConditionVariable;
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.WritableByteChannel

/*
   Creation Time: 12/29/21
   Created by:  Reza Pilehvar
   Maintainers:
      1.  Reza Pilehvar
*/
class CronetRequestCallback : UrlRequest.Callback(){

    private val mResponseCondition = ConditionVariable()

    private var mException: IOException? = null
    private var mResponse : String? = null

    private val mBytesReceived = ByteArrayOutputStream()
    private val mReceiveChannel = Channels.newChannel(mBytesReceived)

    @Throws(IOException::class)
    fun waitForDone(): String? {
        mResponseCondition.block()
        if (mException != null) {
            throw mException!!
        }
        return mResponse
    }

    override fun onRedirectReceived(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        newLocationUrl: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
        request?.read(ByteBuffer.allocateDirect(32 * 1024));
    }

    @Throws(Exception::class)
    override fun onReadCompleted(
        request: UrlRequest?,
        info: UrlResponseInfo?,
        byteBuffer: ByteBuffer?
    ) {
        byteBuffer!!.flip()

        try {
            mReceiveChannel.write(byteBuffer)
        } catch (e: IOException) {
            throw e
        }

        byteBuffer.clear()

        request!!.read(byteBuffer)
    }

    override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
        mResponse = String(mBytesReceived.toByteArray())
        mResponseCondition.open();
    }

    override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
        val e = IOException("Cronet Exception Occurred", error)
        mException = e
        mResponseCondition.open()
    }

    override fun onCanceled(request: UrlRequest?, info: UrlResponseInfo?) {
        mResponseCondition.open();
    }
}