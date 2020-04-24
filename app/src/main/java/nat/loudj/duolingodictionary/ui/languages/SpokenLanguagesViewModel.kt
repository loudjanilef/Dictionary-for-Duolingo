package nat.loudj.duolingodictionary.ui.languages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nat.loudj.duolingodictionary.data.languages.LanguagesRepository
import nat.loudj.duolingodictionary.data.model.Language

class SpokenLanguagesViewModel : ViewModel() {
    private val _languagesList = LanguagesRepository.getSpokenLanguages()
    val languagesList: LiveData<List<Language>> = _languagesList
}