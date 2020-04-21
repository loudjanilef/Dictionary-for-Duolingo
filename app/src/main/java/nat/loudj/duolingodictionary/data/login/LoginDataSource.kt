package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User
import nat.loudj.duolingodictionary.web.WebRequestsHelper
import java.io.IOException

/**
 * Handles authentication with login credentials and retrieves user information.
 */
object LoginDataSource {

    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val request = WebRequestsHelper.createRequest(
                "login",
                Pair("login", username), Pair("password", password)
            )
            val response = WebRequestsHelper.execute(request)
            if (!response.isSuccessful)
                throw Error("Invalid credentials")

            val fakeUser = User(username, response.headers["jwt"] ?: throw Error("No token issued"))
            response.close()
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}

