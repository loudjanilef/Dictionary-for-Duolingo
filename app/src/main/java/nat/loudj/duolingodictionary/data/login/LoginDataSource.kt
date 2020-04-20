package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User
import nat.loudj.duolingodictionary.web.WebRequestsHelper
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val request = WebRequestsHelper.createRequest("google.com", "")
            val response = WebRequestsHelper.execute(request)

            val fakeUser = User(username, "")
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

