package `fun`.inaction.opendoor

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object OpenUtil {

    val okHttpClient = OkHttpClient()

    fun openDoor(callback: (Boolean, String) -> Unit) {

        val request = Request.Builder()
            .url("https://mobileapi.qinlinkeji.com/api/open/doorcontrol/v2/open?sessionId=wxmini:2c9a19587e2fa88a017e5cd05b9a4b7f")
            .post(
                FormBody.Builder()
                    .add("doorControlId", "36691")
                    .add("macAddress", "FC19CA001272")
                    .add("communityId", "5827")
                    .build()
            )
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()
                if (body == null) {
                    callback(false, "response body is null")
                } else {
                    val str = body.string()
                    val jo = JSONObject(str)
                    val code = jo.getInt("code")
                    val msg = jo.getString("message")
                    if (code == 0) {
                        callback(true, msg)
                    } else {
                        callback(false, msg)
                    }
                }
            }

        })
    }

}