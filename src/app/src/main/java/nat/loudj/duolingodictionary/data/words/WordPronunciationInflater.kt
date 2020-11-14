package nat.loudj.duolingodictionary.data.words

import com.github.promeg.pinyinhelper.Pinyin
import nat.loudj.duolingodictionary.data.model.WordWithTranslations
import java.util.*

object WordPronunciationInflater {

    fun inflatePronunciation(
        words: List<WordWithTranslations>,
        languageId: String
    ) = words.map { inflatePronunciation(it, languageId) }

    private fun inflatePronunciation(
        word: WordWithTranslations,
        languageId: String
    ): WordWithTranslations {
        return when (languageId) {
            "zs" -> inflateChinesePronunciation(word)
            else -> word
        }
    }

    private fun inflateChinesePronunciation(word: WordWithTranslations): WordWithTranslations {
        return try {
            val pronunciation = Pinyin.toPinyin(word.word, " ").toLowerCase(Locale.getDefault())
            WordWithTranslations(
                word.word,
                word.translations,
                pronunciation
            )
        } catch (error: Error) {
            word
        }
    }
}
