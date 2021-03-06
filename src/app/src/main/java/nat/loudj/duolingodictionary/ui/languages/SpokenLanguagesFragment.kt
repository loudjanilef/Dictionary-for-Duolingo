package nat.loudj.duolingodictionary.ui.languages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_spoken_languages.view.*
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.data.model.Language
import nat.loudj.duolingodictionary.ui.languages.LanguagesRecyclerViewAdapter.OnLanguagesListInteractionListener

/**
 * A fragment representing the list of user spoken languages
 */
class SpokenLanguagesFragment : Fragment(), OnLanguagesListInteractionListener {
    private val columnCount = 2

    private val languagesRecyclerViewAdapter = LanguagesRecyclerViewAdapter(this)
    private lateinit var spokenLanguagesViewModel: SpokenLanguagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_spoken_languages, container, false)

        spokenLanguagesViewModel = ViewModelProvider(this).get(SpokenLanguagesViewModel::class.java)

        spokenLanguagesViewModel.languagesList.observe(
            viewLifecycleOwner,
            Observer {
                // If we go only one language, navigate to this language
                if (it.size == 1) {
                    goToLanguage(it.first())
                    return@Observer
                }

                languagesRecyclerViewAdapter.setValues(it)
                view.languagesList.scheduleLayoutAnimation()
                view.loader.visibility = View.GONE
            }
        )

        // Set the adapter
        with(view.languagesList) {
            addItemDecoration(
                GridSpacingItemDecorator(
                    16,
                    resources.displayMetrics.density,
                    columnCount
                )
            )
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = languagesRecyclerViewAdapter
        }

        return view
    }

    override fun onLanguageClick(item: Language) = goToLanguage(item)


    private fun goToLanguage(language: Language) {
        val action =
            SpokenLanguagesFragmentDirections.actionSpokenLanguagesFragmentToKnownWordFragment(
                language.id,
                language.name
            )
        findNavController().navigate(action)
    }
}
