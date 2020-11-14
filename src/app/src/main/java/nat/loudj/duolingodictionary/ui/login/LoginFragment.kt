package nat.loudj.duolingodictionary.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.helpers.afterTextChanged
import nat.loudj.duolingodictionary.helpers.hideKeyboard

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        setHasOptionsMenu(true)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            view.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                view.usernameContainer.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                view.passwordContainer.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            view.loading.visibility = View.GONE

            if (loginResult.success != null) {
                val action = LoginFragmentDirections.actionLoginFragmentToSpokenLanguagesFragment()
                findNavController().navigate(action)
            }
        })

        view.username.afterTextChanged {
            view.usernameContainer.error = null
            loginViewModel.loginDataChanged(
                view.username.text.toString(),
                view.password.text.toString()
            )
        }

        view.password.apply {
            afterTextChanged {
                view.passwordContainer.error = null
                loginViewModel.loginDataChanged(
                    view.username.text.toString(),
                    view.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login(
                            view.username.text.toString(),
                            view.password.text.toString()
                        )
                }
                false
            }
        }

        view.login.setOnClickListener {
            login(view.username.text.toString(), view.password.text.toString())
        }

        view.disclaimer.setOnClickListener {
            val openURL =
                if (Build.VERSION.SDK_INT >= 24) Intent(Intent.ACTION_QUICK_VIEW)
                else Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(resources.getString(R.string.privacy_rules_url))
            startActivity(openURL)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) return
        view?.let { view ->
            activity?.let { activity -> hideKeyboard(activity) }
            view.loading.visibility = View.VISIBLE
        }
        GlobalScope.launch { loginViewModel.login(username, password) }
    }
}

