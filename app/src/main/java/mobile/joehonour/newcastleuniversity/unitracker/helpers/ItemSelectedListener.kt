package mobile.joehonour.newcastleuniversity.unitracker.helpers

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

class ItemSelectedListener(private val onSelected: (Any?) -> Unit) : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onSelected(parent?.getItemAtPosition(position))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onSelected(null)
    }

    companion object {

        fun <T> bindItemSelectedListener(
                spinner: Spinner,
                bindingSource: T,
                bindSelectedItem: T.(Any?) -> Unit)
        {
            spinner.onItemSelectedListener = ItemSelectedListener({ bindingSource.bindSelectedItem(it) })
        }
    }
}