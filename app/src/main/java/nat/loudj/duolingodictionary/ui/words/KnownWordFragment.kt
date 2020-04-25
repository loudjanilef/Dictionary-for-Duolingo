package nat.loudj.duolingodictionary.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.ui.words.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [WordsRecyclerViewAdapter.OnWordsListInteractionListener] interface.
 */
class KnownWordFragment : Fragment(), WordsRecyclerViewAdapter.OnWordsListInteractionListener {
    private val wordsRecyclerViewAdapter = WordsRecyclerViewAdapter(DummyContent.ITEMS, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_known_words, container, false)
        val args: KnownWordFragmentArgs by navArgs()
        println(args.languageId)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = wordsRecyclerViewAdapter
            }
        }
        return view
    }

    override fun onWordClick(item: DummyContent.DummyItem) {
        println(item)
    }

}
