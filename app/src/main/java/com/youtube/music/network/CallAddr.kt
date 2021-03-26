package com.youtube.music.network

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject
import java.io.IOException

class CallAddr(val url: String, val isGetRequest: Boolean) {

    private var body = FormBody.Builder()

    suspend fun executeQuery(): String {

        val requestBuilder: Request.Builder = Request.Builder()
        requestBuilder.url(url)
        if (!isGetRequest) {
            requestBuilder.post(body.build())
        }
        val request = requestBuilder.build()

        Log.e("Url", url)

        try {

            val response = withContext(Dispatchers.IO) {
                OkHttpClient.Builder().build().newCall(request).execute()
            }
            Log.e("ResponseCode", response.code.toString());
            if (response.isSuccessful) {
                return response.body?.string() ?: ""
            } else {
                return "";
            }
        } catch (e: Exception) {
            Log.e("tag", Log.getStackTraceString(e))
        }
        return ""
    }

}


/*

class CallAddr(context: Context, hashMap: HashMap<String, Any>, url: String, service: CommonUtilities.Services, isGetRequest: Boolean, optionalHeaders: HashMap<String, Any> = HashMap()) {

    val mContext = context
    private var onWebResponse: OnWebResponse? = null
    private var TAG = javaClass.simpleName
    private var hasmap: HashMap<String, Any>
    private var url: String
    private var service: CommonUtilities.Services
    private var isGetRequest: Boolean? = null
    private var body = FormBody.Builder()
    private var request: Request? = null
    private var headers: HashMap<String, Any>
    private val util = CommonUtilities.instance

    init {
//        this.context = context
        this.onWebResponse = onWebResponse
        this.hasmap = hashMap
        this.url = url
        this.service = service
        this.isGetRequest = isGetRequest
        this.headers = optionalHeaders

        if (isGetRequest) {
            if (service.trackingString.trim().isNotEmpty()) {
                this.url += service.trackingString      // appending action
            }
            val builder: Uri.Builder = Uri.parse(this.url).buildUpon()
            for (entry in hashMap.entries) {
                builder.appendQueryParameter(entry.key, entry.value.toString())
            }
            this.url = builder.build().toString()
        } else {
//            this.url += service.trackingString      // appending action
            if (!this.hasmap.containsKey("action")) {
                this.hasmap["action"] = service.trackingString
            }
            if (!hashMap.containsKey("token")) {
                this.hasmap["token"] = SharedPref.getPrefVal<String>(mContext, Constants.TOKEN, Constants.PREFSTRING)
            }

            this.hasmap.putAll(
                commonParameters(
                    mContext
                )
            )

            for (m in hasmap.entries) {
                if (m.value != null) {
                    body.add(m.key, m.value.toString())
                }
            }
        }

        val builder = StringBuilder()
        this.hasmap.forEach {
            builder.append("\n")
                .append(it.key)
                .append(":")
                .append(it.value.toString())
        }
        util._log(TAG, builder.toString())
    }

    companion object {
        fun commonParameters(context: Context): HashMap<String, Any> {
            val util = CommonUtilities.instance
            val hasmap: HashMap<String, Any> = hashMapOf()
            val city = SharedPref.getPrefVal<String>(context, Constants.CITY)
            val state = SharedPref.getPrefVal<String>(context, Constants.STATE)
            val country = SharedPref.getPrefVal<String>(context, Constants.COUNTRY)
            val address = SharedPref.getPrefVal<String>(context, Constants.ADDRESS)
            if (city.isNotEmpty()) {
                hasmap["city"] = city
            }
            if (state.isNotEmpty()) {
                hasmap["state"] = state
            }
            if (country.isNotEmpty()) {
                hasmap["country"] = country
            }
            if (!address.isEmpty()) {
                hasmap["address"] = address
            }

            hasmap["device"] = "Andro"
            hasmap["is_mob"] = "1"
            hasmap["platform"] = "Andro"
            hasmap["lat"] = SharedPref.getPrefVal<String>(context, Constants.LAT)
            hasmap["lng"] = SharedPref.getPrefVal<String>(context, Constants.LNG)
            hasmap["fcm_token"] = SharedPref.getPrefVal<String>(context, Constants.FCM_TOKEN)
            hasmap["version"] = util.getVersionName(context)
            hasmap["device_version"] = Build.VERSION.SDK_INT
            hasmap["device_name"] = Build.MANUFACTURER + " , " + Build.MODEL
            hasmap["device_id"] = util.getDeviceID(context)
            hasmap["token"] = SharedPref.getPrefVal<String>(context, Constants.TOKEN)

            return hasmap
        }
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    override fun doInBackground(vararg p0: String?): String {
        var result = ""
        try {

            val requestBuilder: Request.Builder = Request.Builder()
            requestBuilder.url(url)
            if (!isGetRequest!!) {
                requestBuilder.post(body.build())
            }
            request = requestBuilder.build()

            CommonUtilities.instance._log("Url $service", url)


            val response: Response = GianiKulwantSingh.instance.client.newCall(request!!).execute()

            CommonUtilities.instance._log(TAG, "Code ${response.code()}")
            result = if (response.isSuccessful) {
                CommonUtilities.instance._log(TAG, "Response is successful")
                //                result = response.toString()
                response.body()!!.string()
            } else {
                ""
            }
        } catch (e: Exception) {
            CommonUtilities.instance._log("Error", Log.getStackTraceString(e))
        }
        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        CommonUtilities.instance._log(service.trackingString + " response", result.toString())
        try {
            val o = JSONObject(result)
            if (o.has("success")) {
                val success = o.optInt("success")
                if (success == 2) {
                    try {
                        SharedPref.logout(mContext)
                        CommonUtilities.instance.hideLoading(mContext)
//                        mContext.startActivity(Intent(mContext, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                    } catch (ex: Exception) {
                        CommonUtilities.instance._log(TAG, Log.getStackTraceString(ex))
                    }
                    return
                }
            }
        } catch (e: Exception) {
            CommonUtilities.instance._log(TAG, Log.getStackTraceString(e))
        }
        onWebResponse?.onWebResponse(this.service, result ?: "")
    }
}

*/