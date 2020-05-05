package nat.loudj.duolingodictionary

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import nat.loudj.duolingodictionary.data.login.LoginRepository

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.loginFragment, R.id.spokenLanguagesFragment))
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)


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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun goToLogin() {
        val action = NavGraphDirections.actionGlobalLoginFragment()
        navController.navigate(action)
    }
}
