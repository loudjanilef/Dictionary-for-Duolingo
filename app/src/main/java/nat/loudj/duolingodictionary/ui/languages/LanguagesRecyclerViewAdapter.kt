package nat.loudj.duolingodictionary.ui.languages


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_language.view.*
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.ui.languages.LanguagesRecyclerViewAdapter.OnLanguagesListInteractionListener
import nat.loudj.duolingodictionary.ui.languages.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnLanguagesListInteractionListener].
 */
class LanguagesRecyclerViewAdapter(
    private val mValues: List<DummyItem>,
    private val mListener: OnLanguagesListInteractionListener
) : RecyclerView.Adapter<LanguagesRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as DummyItem
        mListener.onLanguageClick(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_language, parent, false)
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
            return "${super.toString()} '${mContentView.text}'"
        }
    }

    interface OnLanguagesListInteractionListener {
        fun onLanguageClick(item: DummyItem)
    }
}