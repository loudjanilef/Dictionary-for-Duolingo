package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.PreferencesManager
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
object LoginRepository {
    private const val USERNAME_KEY = "username"
    private const val JWT_KEY = "jwt"

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
        val username = PreferencesManager.read(USERNAME_KEY)
        val jwt = PreferencesManager.read(JWT_KEY)
        if (username != null && jwt != null) {
            user = User(username, jwt)
        }
    }

    fun logout() {
        user = null
        PreferencesManager.delete(USERNAME_KEY)
        PreferencesManager.delete(JWT_KEY)
    }

    suspend fun login(username: String, password: String): Result<User> {
        // handle login
        val result = LoginDataSource.login(username, password)

        if (result is Result.Success) {
            user = result.data
            PreferencesManager.write(USERNAME_KEY, result.data.userName)
            PreferencesManager.write(JWT_KEY, result.data.jwt)
        }

        return result
    }
}
