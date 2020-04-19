package nat.loudj.duolingodictionary.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userName: String,
    val jwt: String
)
