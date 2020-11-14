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
    const val BASE_URL = "www.duolingo.com"
    const val DICTIONARY_URL = "d2.duolingo.com"
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthenticationInterceptor())
        .build()

    suspend fun execute(request: Request): Response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    fun createGetRequest(
        baseUrl: String,
        vararg path: String,
        params: List<Pair<String, String>> = emptyList()
    ): Request {
        val builder = Uri.Builder().scheme("https").authority(baseUrl)
        for (subpath in path) {
            builder.appendPath(subpath)
        }
        for (param in params) {
            builder.appendQueryParameter(param.first, param.second)
        }
        val url = builder.build().toString()

        return Request.Builder().url(url).get().build()
    }

    fun createPostRequest(
        baseUrl: String,
        vararg path: String,
        params: List<Pair<String, String>> = emptyList()
    ): Request {
        val builder = Uri.Builder().scheme("https").authority(baseUrl)
        for (subpath in path) {
            builder.appendPath(subpath)
        }
        val url = builder.build().toString()

        val jsonBody = JSONObject()
        params.forEach { jsonBody.put(it.first, it.second) }
        val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())
        return Request.Builder().url(url).post(requestBody).build()
    }
}