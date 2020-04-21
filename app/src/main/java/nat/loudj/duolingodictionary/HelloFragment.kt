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
 * A simple [Fragment] subclass.
 * Use the [HelloFragment] factory method to
 * create an instance of this fragment.
 */
class HelloFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!LoginRepository.isLoggedIn) {
            navigateToLogin()
        }
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
        return view
    }

    private fun navigateToLogin() {
        val action = HelloFragmentDirections.actionHelloFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}
