package nat.loudj.duolingodictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_hello.view.*
import nat.loudj.duolingodictionary.data.languages.LanguagesRepository
import nat.loudj.duolingodictionary.data.login.LoginRepository
import nat.loudj.duolingodictionary.data.model.Language

/**
 * The base [Fragment] of the app.
 * Will be deleted oncce the app is complete
 */
class HelloFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoginRepository.userToken?.let {
            navigateToLogin()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hello, container, false)
        view.logout.setOnClickListener {
            val observer = Observer<List<Language>> { languages -> println(languages) }
            LanguagesRepository.getSpokenLanguages()
                .observe(viewLifecycleOwner, observer)
//            LoginRepository.logout()
//            navigateToLogin()
        }
        return view
    }

    private fun navigateToLogin() {
        val action = HelloFragmentDirections.actionHelloFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}
