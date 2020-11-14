package nat.loudj.duolingodictionary.ui.words


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_word_with_translation.view.*
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.data.model.WordWithTranslations

/**
 * [RecyclerView.Adapter] that can display a list of [WordWithTranslations]
 */
class WordsRecyclerViewAdapter : RecyclerView.Adapter<WordsRecyclerViewAdapter.ViewHolder>() {
    private var mValues: List<WordWithTranslations> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word_with_translation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.foreignWord.text = item.word
        holder.translations.text = item.translations.joinToString()
        item.pronunciation?.let {
            holder.pronunciation.text = it
            holder.pronunciation.visibility = View.VISIBLE
        }

        with(holder.mView) {
            tag = item
        }
    }

    fun setValues(values: List<WordWithTranslations>) {
        this.mValues = values
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val foreignWord: TextView = mView.foreignWord
        val translations: TextView = mView.translations
        val pronunciation: TextView = mView.pronunciation
    }
}
