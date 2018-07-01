package mobile.joehonour.newcastleuniversity.unitracker.extensions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import mobile.joehonour.newcastleuniversity.unitracker.R

fun Activity.hideKeyboard() {
    hideKeyboard(this.currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

fun <T> Activity.showDeleteItemConfirmationCheckbox(item: T,
                                                    onAcceptDialog: (DialogInterface) -> Unit)
{
    AlertDialog.Builder(this, R.style.AlertDialog)
            .setTitle(getString(R.string.deleteItemDialogTitle))
            .setMessage(getString(R.string.deleteItemDialogMessage).format(item.toString()))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes,
                    { dialog, _ -> onAcceptDialog(dialog) })
            .setNegativeButton(android.R.string.no, null)
            .show()
}

fun Activity.closePageAndShowDeletionMessage(dialog: DialogInterface, deletionMessage: String) {
    dialog.dismiss()
    finish()
    Toast.makeText(
            this,
            deletionMessage,
            Toast.LENGTH_LONG).show()
}

fun Activity.showDeletionMessage(dialog: DialogInterface, deletionMessage: String) {
    dialog.dismiss()
    Toast.makeText(
            this,
            deletionMessage,
            Toast.LENGTH_LONG).show()
}