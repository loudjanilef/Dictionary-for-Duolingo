package nat.loudj.duolingodictionary.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import nat.loudj.duolingodictionary.data.model.WordWithTranslations
import nat.loudj.duolingodictionary.data.words.WordsRepository

/**
 * ViewModel of [KnownWordsFragment] holds a list of known [WordWithTranslations]
 *
 * @param languageId The known words will be retrieved for this language
 */
class KnownWordsViewModel(languageId: String) : ViewModel() {
    val searchTerm = MutableLiveData("")
    private val retrievedWordsList = WordsRepository.getKnownWords(languageId)
    private val searchedWordsList =
        Transformations.switchMap(searchTerm) { searchTermInWordsList(it, retrievedWordsList) }
    val wordsList = Transformations.map(searchedWordsList) { sortAndFilterWords(it) }

    private fun searchTermInWordsList(
        term: String,
        wordsList: LiveData<List<WordWithTranslations>>
    ) = Transformations.map(wordsList) {
        it.filter { word -> word.matchesTerm(term) }
    }

    private fun sortAndFilterWords(words: List<WordWithTranslations>) =
        words.filter { !it.translations.isNullOrEmpty() }
            .sortedBy { it.word }
}