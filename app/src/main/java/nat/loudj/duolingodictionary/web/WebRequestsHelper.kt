package nat.loudj.duolingodictionary.web

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object WebRequestsHelper {
    private val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()

    fun execute(request: Request): Response = client.newCall(request).execute()
}