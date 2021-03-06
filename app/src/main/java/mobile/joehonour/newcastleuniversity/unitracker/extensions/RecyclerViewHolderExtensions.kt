package mobile.joehonour.newcastleuniversity.unitracker.extensions

import android.support.v7.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.listenForClick(event: (position: Int) -> Unit): T
{
    itemView.setOnClickListener {
        event.invoke(adapterPosition)
    }
    return this
}

fun <T : RecyclerView.ViewHolder> T.listenForLongClick(event: (position: Int) -> Unit): T
{
    itemView.setOnLongClickListener {
        event.invoke(adapterPosition)
        true
    }
    return this
}
