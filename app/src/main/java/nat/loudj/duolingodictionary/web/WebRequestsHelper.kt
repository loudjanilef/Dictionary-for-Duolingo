package nat.loudj.duolingodictionary.web

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject

object WebRequestsHelper {
    private val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()
    const val BASE_URL = "www.duolingo.com"

    suspend fun execute(request: Request): Response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    fun createRequest(
        path: String,
        vararg params: Pair<String, String>
    ): Request {
        val url =
            Uri.Builder().scheme("https").authority(BASE_URL).appendPath(path).build().toString()
        val request = Request.Builder().url(url)

        if (params.isNullOrEmpty())
            return request.get().build()

        val jsonBody = JSONObject()
        params.forEach { jsonBody.put(it.first, it.second) }
        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return request.post(requestBody).build()
    }
}