package nat.loudj.duolingodictionary.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nat.loudj.duolingodictionary.R
import nat.loudj.duolingodictionary.data.Result
import nat.loudj.duolingodictionary.data.login.LoginRepository

class LoginViewModel : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    suspend fun login(username: String, password: String) = withContext(Dispatchers.Main) {
        val result = LoginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value = LoginResult(success = result.data)
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
            _loginForm.value = LoginFormState(passwordError = R.string.wrong_credentials)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            val areFieldsNonEmpty = username.isNotEmpty() && password.isNotEmpty()
            _loginForm.value = LoginFormState(isDataValid = areFieldsNonEmpty)
        }
    }

    /**
     * [username] validation
     *
     * @param username
     * @return
     */
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else true

    }
}
