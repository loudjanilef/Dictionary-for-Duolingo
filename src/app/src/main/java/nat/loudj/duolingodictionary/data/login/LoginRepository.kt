package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.PreferencesManager
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User

/**
 * Object that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 * Authenticated user token is saved in preferences
 */
object LoginRepository {
    private const val USERNAME_KEY = "username"
    private const val USER_ID = "user_id"
    private const val JWT_KEY = "jwt"

    /**
     * Current connected user
     */
    var user: User? = null
        private set

    /**
     * Load user from storage
     */
    init {
        user = null
        val username = PreferencesManager.read(USERNAME_KEY)
        val userId = PreferencesManager.read(USER_ID)
        val jwt = PreferencesManager.read(JWT_KEY)
        if (username != null && userId != null && jwt != null) {
            user = User(username, userId, jwt)
        }
    }

    /**
     * Delete cached and saved user
     */
    fun logout() {
        user = null
        PreferencesManager.delete(USERNAME_KEY)
        PreferencesManager.delete(USER_ID)
        PreferencesManager.delete(JWT_KEY)
    }

    /**
     * Authenticates the user with the data source and get its credentials
     * Saves the authenticated user in the preferences
     *
     * @param username
     * @param password
     * @return Authentication result
     */
    suspend fun login(username: String, password: String): Result<User> {
        // handle login
        val result = LoginDataSource.login(username, password)

        if (result is Result.Success) {
            user = result.data
            PreferencesManager.write(USERNAME_KEY, result.data.userName)
            PreferencesManager.write(USER_ID, result.data.userId)
            PreferencesManager.write(JWT_KEY, result.data.jwt)
        }

        return result
    }
}
