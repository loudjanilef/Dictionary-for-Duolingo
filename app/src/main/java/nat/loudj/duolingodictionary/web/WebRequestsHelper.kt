package nat.loudj.duolingodictionary.web

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object WebRequestsHelper {
    private val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()

    suspend fun execute(request: Request): Response = withContext(Dispatchers.IO) {
        client.newCall(request).execute()
    }

    fun createRequest(baseUrl: String, path: String, vararg params: Pair<String, String>): Request {
        val url = buildUrl(baseUrl, path, *params)
        return Request.Builder().url(url).build()
    }

    private fun buildUrl(
        baseUrl: String,
        path: String,
        vararg params: Pair<String, String>
    ): String {
        val completeUrl = Uri.Builder().scheme("https").authority(baseUrl).appendPath(path)
        params.forEach { completeUrl.appendQueryParameter(it.first, it.second) }
        return completeUrl.build().toString()
    }
}