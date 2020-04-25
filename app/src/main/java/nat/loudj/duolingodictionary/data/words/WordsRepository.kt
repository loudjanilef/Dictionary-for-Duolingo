package nat.loudj.duolingodictionary.data.words

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.WordWithTranslations

/**
 * Objects that handle getting words
 */
object WordsRepository {

    /**
     * Get known words for specified [languageId]
     *
     * @param languageId
     * @return
     */
    fun getKnownWords(languageId: String): MutableLiveData<List<WordWithTranslations>> {
        val knownWords = MutableLiveData<List<WordWithTranslations>>()
        GlobalScope.launch(Dispatchers.Main) {
            val result = WordsDataSource.getKnownWords(languageId)

            if (result is Result.Success)
                knownWords.value = result.data
        }
        return knownWords
    }
}