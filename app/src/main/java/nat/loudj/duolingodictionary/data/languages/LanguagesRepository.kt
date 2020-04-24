package nat.loudj.duolingodictionary.data.languages

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.model.Language

object LanguagesRepository {
    fun getSpokenLanguages(): MutableLiveData<List<Language>> {
        val spokenlanguages = MutableLiveData<List<Language>>()
        GlobalScope.launch(Dispatchers.Main) {
            val result = LanguagesDataSource.getSpokenLanguages()

            if (result is Result.Success)
                spokenlanguages.value = result.data
        }
        return spokenlanguages
    }
}