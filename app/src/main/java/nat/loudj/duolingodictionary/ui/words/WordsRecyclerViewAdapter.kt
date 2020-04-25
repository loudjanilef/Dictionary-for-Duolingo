package nat.loudj.duolingodictionary.ui.words


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_word_with_translation.view.*
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.ui.words.WordsRecyclerViewAdapter.OnWordsListInteractionListener
import nat.loudj.duolingodictionary.ui.words.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnWordsListInteractionListener].
 */
class WordsRecyclerViewAdapter(
    private val mValues: List<DummyItem>,
    private val mListener: OnWordsListInteractionListener
) : RecyclerView.Adapter<WordsRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener = View.OnClickListener { v ->
        val item = v.tag as DummyItem
        mListener.onWordClick(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_word_with_translation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.id
        holder.mContentView.text = item.content

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    /**
     * This interface must be implemented by all the elements that need to react on list element click
     *
     */
    interface OnWordsListInteractionListener {
        fun onWordClick(item: DummyItem)
    }
}
