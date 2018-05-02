package mobile.joehonour.newcastleuniversity.unitracker.helpers

import android.widget.Button

/**
 * Provides the ability to register a click event handler to a button, as an extension method of another
 * item. This allows for nice readability when registering event handlers with view models.
 *
 * @param button the button you wish to register an event handler for.
 * @param bindingSource the source you wish to bind the button event handler to.
 * @param onClicked is executed every time the button is clicked.
 */
fun <T> bindButtonClickedListener(
        button: Button,
        bindingSource: T,
        onClicked: T.() -> Unit)
{
    button.setOnClickListener { bindingSource.onClicked() }
}
