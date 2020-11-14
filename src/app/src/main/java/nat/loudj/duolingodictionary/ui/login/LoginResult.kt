package nat.loudj.duolingodictionary.ui.login

import nat.loudj.duolingodictionary.data.model.User

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: User? = null,
    val error: Int? = null
)
