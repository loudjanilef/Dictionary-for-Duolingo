package nat.loudj.duolingodictionary

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFERENCES_NAME = "DULINGO_DICTIONARY"
    private val sharedPrefs: SharedPreferences =
        AppWithContext.appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun read(key: String, defaultValue: String? = null): String? =
        sharedPrefs.getString(key, defaultValue)

    fun write(key: String, value: String) {
        with(sharedPrefs.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun delete(key: String) {
        with(sharedPrefs.edit()) {
            remove(key)
            commit()
        }
    }
}