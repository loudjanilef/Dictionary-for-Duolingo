package nat.loudj.duolingodictionary.ui.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.data.model.WordWithTranslations

/**
 * A fragment representing the list of known words for a language
 */
class KnownWordsFragment : Fragment(), WordsRecyclerViewAdapter.OnWordsListInteractionListener {
    private val wordsRecyclerViewAdapter = WordsRecyclerViewAdapter(this)
    private lateinit var knownWordsViewModel: KnownWordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_known_words, container, false)
        val args: KnownWordsFragmentArgs by navArgs()

        knownWordsViewModel =
            ViewModelProvider(this, KnownWordsViewModelFactory(args.languageId)).get(
                KnownWordsViewModel::class.java
            )

        knownWordsViewModel.wordsList.observe(
            viewLifecycleOwner,
            Observer { wordsRecyclerViewAdapter.setValues(it) }
        )

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = wordsRecyclerViewAdapter
            }
        }
        return view
    }

    override fun onWordClick(item: WordWithTranslations) {
        println(item)
    }

}
