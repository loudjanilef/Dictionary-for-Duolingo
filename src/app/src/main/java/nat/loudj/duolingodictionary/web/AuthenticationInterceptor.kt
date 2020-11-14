package nat.loudj.duolingodictionary.web

import nat.loudj.duolingodictionary.data.login.LoginRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()

        LoginRepository.user?.let { token ->
            requestBuilder.addHeader(
                "Authorization",
                "Bearer ${token.jwt}"
            )
        }

        request = requestBuilder.build()
        return chain.proceed(request)
    }
}