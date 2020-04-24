package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.UserToken
import nat.loudj.duolingodictionary.web.WebRequestsManager
import java.io.IOException

/**
 * Object that handles authentication with login credentials and retrieves user information.
 */
object LoginDataSource {

    /**
     * Login the user on the distant server
     *
     * @param username
     * @param password
     * @return The user or an error
     */
    suspend fun login(username: String, password: String): Result<UserToken> {
        return try {
            val request = WebRequestsManager.createRequest(
                listOf("login"),
                Pair("login", username),
                Pair("password", password)
            )
            val response = WebRequestsManager.execute(request)
            if (!response.isSuccessful)
                throw Error("Invalid credentials")

            val userToken = UserToken(
                username, response.headers["jwt"]
                    ?: throw Error("No token issued")
            )
            response.close()
            Result.Success(userToken)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}

