package nat.loudj.duolingodictionary.data.words

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.login.LoginRepository
import nat.loudj.duolingodictionary.data.model.WordWithTranslations
import nat.loudj.duolingodictionary.web.WebRequestsManager
import org.json.JSONObject
import java.io.IOException

object WordsDataSource {
    suspend fun getKnownWords(languageId: String): Result<List<WordWithTranslations>> {
        val username = LoginRepository.user?.userName
            ?: return Result.Error(IllegalStateException("Not connected"))

        return try {
            val wordsRequest =
                WebRequestsManager.createGetRequest(WebRequestsManager.BASE_URL, "users", username)
            val wordsResponse = WebRequestsManager.execute(wordsRequest)

            if (!wordsResponse.isSuccessful)
                throw Error("Invalid credentials")

            val (knownWords, learningLanguageId) = withContext(Dispatchers.IO) {
                val bodyString = wordsResponse.body?.string() ?: throw  Error("Nothing returned")
                val bodyJson = JSONObject(bodyString)
                val knownWords = adaptKnownWords(bodyJson, languageId)
                val learningLanguageId = adaptLearningLanguageKey(bodyJson)
                Pair(knownWords, learningLanguageId)
            }

            val translationsRequest = WebRequestsManager.createGetRequest(
                WebRequestsManager.DICTIONARY_URL,
                "api",
                "1",
                "dictionary",
                "hints",
                languageId,
                learningLanguageId,
                params = listOf(
                    Pair(
                        "tokens",
                        knownWords.joinToString(
                            prefix = "[\"",
                            separator = "\",\"",
                            postfix = "\"]"
                        )
                    )
                )
            )
            val translationsResponse = WebRequestsManager.execute(translationsRequest)

            val knownWordsWithTranslations = withContext(Dispatchers.IO) {
                val bodyString =
                    translationsResponse.body?.string() ?: throw  Error("Nothing returned")
                val bodyJson = JSONObject(bodyString)
                adaptKnownWordsWithTranslations(bodyJson)
            }

            Result.Success(knownWordsWithTranslations)
        } catch (e: Throwable) {
            Result.Error(IOException("Error retrieving languages", e))
        }
    }

    private fun adaptLearningLanguageKey(json: JSONObject): String = json.getString("ui_language")

    private fun adaptKnownWords(json: JSONObject, languageId: String): List<String> {
        val knownWords = mutableListOf<String>()

        val language = json.getJSONObject("language_data").optJSONObject(languageId)
            ?: return emptyList()
        val skills = language.getJSONArray("skills")

        for (skillIndex in 0 until skills.length()) {
            val skill = skills.getJSONObject(skillIndex)
            val learned = skill.getBoolean("learned")
            if (learned) {
                val words = skill.getJSONArray("words")
                for (wordIndex in 0 until words.length()) {
                    knownWords.add(words.getString(wordIndex))
                }
            }
        }

        return knownWords
    }

    private fun adaptKnownWordsWithTranslations(json: JSONObject): List<WordWithTranslations> {
        val wordsWithTranslations = mutableListOf<WordWithTranslations>()
        val words = json.keys()
        for (word in words) {
            val translationsArray = json.getJSONArray(word)
            val translations = mutableListOf<String>()
            for (translationIndex in 0 until translationsArray.length()) {
                translations.add(translationsArray.getString(translationIndex))
            }
            wordsWithTranslations.add(WordWithTranslations(word, translations))
        }
        return wordsWithTranslations
    }
}