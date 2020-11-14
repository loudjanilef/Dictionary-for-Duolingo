package nat.loudj.duolingodictionary

import android.content.Context
import android.content.SharedPreferences

/**
 * Object that handle access to app preferences
 */
object PreferencesManager {
    private const val PREFERENCES_NAME = "DULINGO_DICTIONARY"
    private val sharedPrefs: SharedPreferences =
        AppWithContext.appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * Read the value corresponding to the [key] from the preferences
     * If the key is not present in the preferences, return [defaultValue]
     *
     * @param key
     * @param defaultValue
     * @return The read value
     */
    fun read(key: String, defaultValue: String? = null): String? =
        sharedPrefs.getString(key, defaultValue)

    /**
     * Write the [value] in the preferences under the [key]
     *
     * @param key
     * @param value
     */
    fun write(key: String, value: String) {
        with(sharedPrefs.edit()) {
            putString(key, value)
            commit()
        }
    }

    /**
     * Remove the value corresponding to the [key] from the preferences
     *
     * @param key
     */
    fun delete(key: String) {
        with(sharedPrefs.edit()) {
            remove(key)
            commit()
        }
    }
}