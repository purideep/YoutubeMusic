package com.youtube.music.network

import android.content.Context
import android.net.Uri
import android.util.Log
import com.youtube.music.extensions.await
import com.youtube.music.util.SharedPref
import kotlinx.coroutines.*
import okhttp3.*
import java.io.File

class CallAddr(
    var hashMap: HashMap<String, Any>,
    var url: String,
    val service: Services?,
    val isGetRequest: Boolean = false,
    val headers: HashMap<String, Any> = HashMap()
) {
    private var TAG = javaClass.simpleName
    private var body = FormBody.Builder()

    init {

        if (service != null && service.trackingString.trim().isNotEmpty()) {
            this.url += service.trackingString      // appending action
        }

        if (isGetRequest) {
            val builder: Uri.Builder = Uri.parse(this.url).buildUpon()
            for (entry in hashMap.entries) {
                builder.appendQueryParameter(entry.key, entry.value.toString())
            }
            this.url = builder.build().toString()
        } else {
            for (m in this.hashMap.entries) {
                body.add(m.key, m.value.toString())
            }
        }

        val builder = StringBuilder()
        this.hashMap.forEach {
            builder.append("\n")
                .append(it.key)
                .append(":")
                .append(it.value.toString())
        }
        Log.e(TAG, builder.toString())
    }

    suspend fun execute(): String {
        var result = ""
        GlobalScope.async(Dispatchers.IO) {
            try {
                val requestBuilder: Request.Builder = Request.Builder()
                headers.keys.forEach { requestBuilder.addHeader(it, headers.get(it).toString()) }
                requestBuilder.url(url)
                if (!isGetRequest) {
                    requestBuilder.post(body.build())
                }
                Log.e("Url $service", url)
                val response: Response =
                    OkHttpClient.Builder().cache(
                        Cache(
                            directory = File(SharedPref.cacheDir, "http_cache"),
                            // $0.05 worth of phone storage in 2020
                            maxSize = 50L * 1024L * 1024L // 50 MiB
                        )
                    ).build().newCall(requestBuilder.build()).await()
                Log.e(TAG, "Code ${response.code}")
                result = response.body?.string() ?: ""
            } catch (e: Exception) {
                Log.e("Error", Log.getStackTraceString(e))
            }

        }.await()
        return result;
    }
}