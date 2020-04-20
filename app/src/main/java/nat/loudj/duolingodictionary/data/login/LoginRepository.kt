package nat.loudj.duolingodictionary.data.login

import nat.loudj.duolingodictionary.PreferencesManager
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
        val username = PreferencesManager.read("username")
        val jwt = PreferencesManager.read("jwt")
        if (username != null && jwt != null) {
            user = User(username, jwt)
        }
    }

    fun logout() {
        user = null
        PreferencesManager.delete("username")
        PreferencesManager.delete("jwt")
    }

    suspend fun login(username: String, password: String): Result<User> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            user = result.data
            PreferencesManager.write("username", result.data.userName)
            PreferencesManager.write("jwt", result.data.jwt)
        }

        return result
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: LoginRepository(dataSource = LoginDataSource()).also { instance = it }
            }
    }
}
