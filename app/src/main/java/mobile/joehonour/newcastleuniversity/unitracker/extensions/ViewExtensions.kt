package mobile.joehonour.newcastleuniversity.unitracker.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Provides the ability to hide the keyboard if it is displayed.
 */
fun Activity.hideKeyboard() {
    hideKeyboard(this.currentFocus ?: View(this))
}

/**
 * Provides the ability to hide the keyboard if it is displayed.
 */
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}