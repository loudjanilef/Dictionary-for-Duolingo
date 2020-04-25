package nat.loudj.duolingodictionary.data.login

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User
import nat.loudj.duolingodictionary.web.WebRequestsManager
import org.json.JSONObject
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
    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val request = WebRequestsManager.createPostRequest(
                WebRequestsManager.BASE_URL,
                "login",
                params = listOf(
                    Pair("login", username),
                    Pair("password", password)
                )
            )
            val response = WebRequestsManager.execute(request)
            if (!response.isSuccessful)
                throw Error("Invalid credentials")

            val user = withContext(Dispatchers.IO) {
                val bodyString = response.body?.string() ?: throw  Error("Nothing returned")
                val jsonBody = JSONObject(bodyString)
                val jwt = response.headers["jwt"] ?: throw Error("No token issued")
                adaptUser(jsonBody, jwt)
            }

            Result.Success(user)
        } catch (e: Throwable) {
            Log.e(this::class.simpleName, e.message ?: "Error")
            Result.Error(IOException("Error logging in", e))
        }
    }

    private fun adaptUser(json: JSONObject, jwt: String): User {
        val username = json.getString("username")
        val id = json.getString("user_id")
        return User(username, id, jwt)
    }
}

