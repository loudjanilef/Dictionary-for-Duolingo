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

object WebRequestsManager {
    private const val BASE_URL = "www.duolingo.com"
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor())
        .build()

    suspend fun execute(request: Request): Response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    fun createRequest(
        path: List<String>,
        vararg params: Pair<String, String>
    ): Request {
        val builder = Uri.Builder().scheme("https").authority(BASE_URL)
        for (subpath in path) {
            builder.appendPath(subpath)
        }
        val url = builder.build().toString()

        val request = Request.Builder().url(url)

        if (params.isNullOrEmpty())
            return request.get().build()

        val jsonBody = JSONObject()
        params.forEach { jsonBody.put(it.first, it.second) }
        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return request.post(requestBody).build()
    }
}