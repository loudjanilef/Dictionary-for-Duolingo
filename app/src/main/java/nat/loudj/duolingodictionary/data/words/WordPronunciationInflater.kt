package nat.loudj.duolingodictionary.data.words

import nat.loudj.duolingodictionary.data.model.WordWithTranslations
import net.duguying.pinyin.Pinyin

object WordPronunciationInflater {
    private val pinyinTranslater = Pinyin()

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
        val pronunciation = pinyinTranslater.translateWithSep(word.word).replace(',', ' ')
        return if (pronunciation != word.word)
            WordWithTranslations(
                word.word,
                word.translations,
                pronunciation
            )
        else word
    }
}
