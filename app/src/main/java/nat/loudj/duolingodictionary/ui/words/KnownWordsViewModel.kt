package nat.loudj.duolingodictionary.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nat.loudj.duolingodictionary.data.model.WordWithTranslations
import nat.loudj.duolingodictionary.data.words.WordsRepository

/**
 * ViewModel of [KnownWordsFragment] holds a list of known [WordWithTranslations]
 *
 * @param languageId The known words will be retrieved for this language
 */
class KnownWordsViewModel(languageId: String) : ViewModel() {
    private val _wordsList = WordsRepository.getKnownWords(languageId)
    val wordsList: LiveData<List<WordWithTranslations>> = _wordsList
}