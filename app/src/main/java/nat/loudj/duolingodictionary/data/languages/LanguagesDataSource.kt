package nat.loudj.duolingodictionary.data.languages

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.login.LoginRepository
import nat.loudj.duolingodictionary.data.model.Language
import nat.loudj.duolingodictionary.web.WebRequestsManager
import org.json.JSONObject
import java.io.IOException

object LanguagesDataSource {
    suspend fun getSpokenLanguages(): Result<List<Language>> {
        val username = LoginRepository.user?.userName
            ?: return Result.Error(IllegalStateException("Not connected"))

        return try {
            val request = WebRequestsManager.createRequest(listOf("users", username))
            val response = WebRequestsManager.execute(request)

            if (!response.isSuccessful)
                throw Error("Invalid credentials")

            val languagesList = withContext(Dispatchers.IO) {
                val bodyString = response.body?.string() ?: throw  Error("Nothing returned")
                val bodyJson = JSONObject(bodyString)
                adaptSpokenLanguages(bodyJson)
            }

            Result.Success(languagesList)
        } catch (e: Throwable) {
            Result.Error(IOException("Error retrieving languages", e))
        }
    }

    private fun adaptSpokenLanguages(json: JSONObject): List<Language> {
        val learnedLanguages = mutableListOf<Language>()
        val allLanguages = json.getJSONArray("languages")

        for (index in 0 until allLanguages.length()) {
            val language = allLanguages.getJSONObject(index)
            val learned = language.getBoolean("learning")

            if (learned) {
                val name = language.getString("language_string")
                val id = language.getString("language")
                learnedLanguages.add(Language(name, id))
            }
        }

        return learnedLanguages
    }
}