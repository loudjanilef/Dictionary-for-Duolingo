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
    private val loginRepository = LoginRepository.getInstance()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    suspend fun login(username: String, password: String) = withContext(Dispatchers.Main) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

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
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
