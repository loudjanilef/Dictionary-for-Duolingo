package nat.loudj.duolingodictionary.data.model

data class WordWithTranslations(
    val word: String,
    val translations: List<String>,
    val pronunciation: String? = null
) {
    fun matchesTerm(term: String): Boolean =
        word.startsWith(term, true) or translations.any {
            it.startsWith(
                term,
                true
            )
        } or (pronunciation?.startsWith(term, true) ?: false)
}