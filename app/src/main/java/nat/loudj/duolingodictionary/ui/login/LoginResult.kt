package nat.loudj.duolingodictionary.ui.login

import nat.loudj.duolingodictionary.data.model.UserToken

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: UserToken? = null,
    val error: Int? = null
)
