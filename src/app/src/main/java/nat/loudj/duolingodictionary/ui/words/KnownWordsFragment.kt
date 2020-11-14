package nat.loudj.duolingodictionary.ui.words

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_known_words.view.*
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.helpers.afterTextChanged

/**
 * A fragment representing the list of known words for a language
 */
class KnownWordsFragment : Fragment() {
    private val wordsRecyclerViewAdapter = WordsRecyclerViewAdapter()
    private lateinit var knownWordsViewModel: KnownWordsViewModel

    @SuppressLint("ClickableViewAccessibility")
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
            {
                val oldItemCount = wordsRecyclerViewAdapter.itemCount
                wordsRecyclerViewAdapter.setValues(it)
                if (oldItemCount == 0)
                    view.wordsList.scheduleLayoutAnimation()
                view.loader.visibility = View.GONE
            }
        )

        // Set the adapter
        with(view.wordsList) {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = wordsRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }

        with(view.search) {
            onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus)
                        (view as MotionLayout).transitionToStart()
                }
            afterTextChanged { searchTerm -> knownWordsViewModel.searchTerm.value = searchTerm }
        }

        view.searchActionButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                view.search.requestFocus()
            }
            false
        }

        return view
    }
}
