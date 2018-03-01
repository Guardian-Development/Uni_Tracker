package mobile.joehonour.newcastleuniversity.unitracker.view.support

import android.widget.Button

fun <T> bindButtonClickedListener(
        button: Button,
        bindingSource: T,
        onClicked: T.() -> Unit) {

    button.setOnClickListener { bindingSource.onClicked() }
}
