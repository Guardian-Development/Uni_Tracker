package mobile.joehonour.newcastleuniversity.unitracker.extensions

import android.support.v7.widget.RecyclerView

/**
 * Provides the ability to listen to a click event on a list item, passing the position of the list
 * item clicked to the event listener.
 *
 * @param event is executed when an item within the list is clicked, being passed the item position.
 */
fun <T : RecyclerView.ViewHolder> T.listenForClick(event: (position: Int) -> Unit): T
{
    itemView.setOnClickListener {
        event.invoke(adapterPosition)
    }
    return this
}
