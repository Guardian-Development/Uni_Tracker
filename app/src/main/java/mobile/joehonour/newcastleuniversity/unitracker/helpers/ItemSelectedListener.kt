package mobile.joehonour.newcastleuniversity.unitracker.helpers

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

/**
 * Provides the ability to get the item selected within an adapter view. It allows for the function to
 * be executed with the item at the selected position being passed in.
 *
 * @param onSelected is executed every time an item is selected, being passed the item.
 */
class ItemSelectedListener(private val onSelected: (Any?) -> Unit) : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onSelected(parent?.adapter?.getItem(position))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        onSelected(null)
    }

    companion object {

        /**
         * Provides a binding function to be used when registering item selection event handlers.
         *
         * @param spinner the spinner you wish to bind to.
         * @param bindingSource the source you wish to bind to when an item is selected.
         * @param bindSelectedItem the function that is executed when an item is selected.
         */
        fun <T> bindItemSelectedListener(
                spinner: Spinner,
                bindingSource: T,
                bindSelectedItem: T.(Any?) -> Unit)
        {
            spinner.onItemSelectedListener = ItemSelectedListener({ bindingSource.bindSelectedItem(it) })
        }
    }
}