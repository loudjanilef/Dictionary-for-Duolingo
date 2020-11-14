package nat.loudj.duolingodictionary.ui.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory for producing the KnownWordsViewModel with parameter [languageId]
 *
 * @property languageId
 */
class KnownWordsViewModelFactory(private val languageId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(KnownWordsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            KnownWordsViewModel(languageId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}