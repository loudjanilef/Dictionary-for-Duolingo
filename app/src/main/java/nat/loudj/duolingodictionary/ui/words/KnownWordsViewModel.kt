package nat.loudj.duolingodictionary.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nat.loudj.duolingodictionary.data.model.WordWithTranslations
import nat.loudj.duolingodictionary.data.words.WordsRepository

class KnownWordsViewModel(languageId: String) : ViewModel() {
    private val _wordsList = WordsRepository.getKnownWords(languageId)
    val wordsList: LiveData<List<WordWithTranslations>> = _wordsList
}