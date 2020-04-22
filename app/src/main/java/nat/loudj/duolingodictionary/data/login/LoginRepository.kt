package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.PreferencesManager
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.UserToken

/**
 * Object that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 * Authenticated user token is saved in preferences
 */
object LoginRepository {
    private const val USERNAME_KEY = "username"
    private const val JWT_KEY = "jwt"

    /**
     * Current connected user
     */
    var userToken: UserToken? = null
        private set

    val isLoggedIn: Boolean
        get() = userToken != null

    /**
     * Load user from storage
     */
    init {
        userToken = null
        val username = PreferencesManager.read(USERNAME_KEY)
        val jwt = PreferencesManager.read(JWT_KEY)
        if (username != null && jwt != null) {
            userToken = UserToken(username, jwt)
        }
    }

    /**
     * Delete cached and saved user
     */
    fun logout() {
        userToken = null
        PreferencesManager.delete(USERNAME_KEY)
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
    suspend fun login(username: String, password: String): Result<UserToken> {
        // handle login
        val result = LoginDataSource.login(username, password)

        if (result is Result.Success) {
            userToken = result.data
            PreferencesManager.write(USERNAME_KEY, result.data.userName)
            PreferencesManager.write(JWT_KEY, result.data.jwt)
        }

        return result
    }
}
