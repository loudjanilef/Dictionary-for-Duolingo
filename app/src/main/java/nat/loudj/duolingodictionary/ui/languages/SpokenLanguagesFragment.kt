package nat.loudj.duolingodictionary.ui.languages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.ui.languages.LanguagesRecyclerViewAdapter.OnLanguagesListInteractionListener
import nat.loudj.duolingodictionary.ui.languages.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [LanguagesRecyclerViewAdapter.OnLanguagesListInteractionListener] interface.
 */
class SpokenLanguagesFragment : Fragment(), OnLanguagesListInteractionListener {
    private val columnCount = 2

    private var listener: OnLanguagesListInteractionListener = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spoken_languages, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = LanguagesRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onLanguageClick(item: DummyContent.DummyItem) {
        println(item.content)
    }
}
