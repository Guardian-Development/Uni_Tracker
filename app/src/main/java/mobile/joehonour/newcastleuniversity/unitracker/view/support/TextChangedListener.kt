package mobile.joehonour.newcastleuniversity.unitracker.view.support

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

class TextChangedListener(private val onChanged: (String?) -> Unit) : TextWatcher
{
    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(value: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun onTextChanged(value: CharSequence?, start: Int, count: Int, after: Int)
    {
        onChanged(value?.toString())
    }

    companion object {

        fun <T> bindTextChangedListener(
                textView: TextView,
                bindingSource: T,
                bindChangeToState: T.(String?) -> Unit)
        {
            textView.addTextChangedListener(TextChangedListener { bindingSource.bindChangeToState(it) })
        }
    }
}