package nat.loudj.duolingodictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_hello.view.*
import nat.loudj.duolingodictionary.data.login.LoginRepository

/**
 * The base [Fragment] of the app.
 * Will be deleted oncce the app is complete
 */
class HelloFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoginRepository.user ?: navigateToLogin()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hello, container, false)
        view.logout.setOnClickListener {
            LoginRepository.logout()
            navigateToLogin()
        }
        view.goToLanguages.setOnClickListener {
            navigateToSpokenLanguages()
        }
        return view
    }

    private fun navigateToLogin() {
        val action = HelloFragmentDirections.actionHelloFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSpokenLanguages() {
        val action = HelloFragmentDirections.actionHelloFragmentToSpokenLanguagesFragment()
        findNavController().navigate(action)
    }
}
