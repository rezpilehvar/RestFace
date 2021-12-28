package ir.irezaa.networkdispatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.irezaa.networkdispatcher.converter.gson.NDGsonDataConverter
import ir.irezaa.networkdispatcher.core.NDNetworkClient
import ir.irezaa.networkdispatcher.core.NDNetworkRequest
import ir.irezaa.networkdispatcher.engine.cronet.NDCronetEngine
import ir.irezaa.networkdispatcher.engine.okhttp.NDOkHttpRequestBodyEncoder
import ir.irezaa.networkdispatcher.engine.okhttp.NDOkHttpResponseBodyDecoder
import ir.irezaa.networkdispatcher.engine.okhttp.OkHttpNetworkEngine
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.chromium.net.CronetEngine

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient.Builder().build()
        val gsonConverter = NDGsonDataConverter(Gson())

        val okHttpNetworkEngine = OkHttpNetworkEngine(
            okHttpClient,
            NDOkHttpRequestBodyEncoder(gsonConverter),
            NDOkHttpResponseBodyDecoder(gsonConverter)
        )


        val cronetClient = CronetEngine.Builder(this).build()
        val cronetEngine = NDCronetEngine(cronetClient,gsonConverter)

        val ndNetworkClient = NDNetworkClient.Builder(cronetEngine)
            .build()

        val request = NDNetworkRequest.GET.Builder("https://jsonplaceholder.typicode.com/todos/1",TodoResponse::class.java)
            .build()

        Thread(Runnable {
            val response = ndNetworkClient.execute(request)

            println("todoID : $response")
        }).start()

    }
}