package nat.loudj.duolingodictionary.helpers

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Hide the keyboard from anywhere
 * Code from https://stackoverflow.com/questions/1109022/close-hide-android-soft-keyboard
 *
 * @param activity
 */
fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}