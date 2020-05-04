package nat.loudj.duolingodictionary

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import nat.loudj.duolingodictionary.data.login.LoginRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoginRepository.user ?: goToLogin()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            LoginRepository.logout()
            goToLogin()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun goToLogin() {
        val action = NavGraphDirections.actionGlobalLoginFragment()
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(action)
    }
}
