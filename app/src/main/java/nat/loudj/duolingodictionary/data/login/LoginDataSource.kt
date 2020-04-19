package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<User> {
        return try {
            // TODO: handle loggedInUser authentication
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

