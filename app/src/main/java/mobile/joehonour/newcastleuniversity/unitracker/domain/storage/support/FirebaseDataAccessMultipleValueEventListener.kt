package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FirebaseDataAccessMultipleValueEventListener<T : Any>(
        private val onError: (String?) -> Unit,
        private val onSuccess: (List<T>) -> Unit,
        private val type: Class<T>) : ValueEventListener
{
    override fun onDataChange(data: DataSnapshot?)
    {
        try {
            val children = data?.children
            when(children)
            {
                null -> onSuccess(emptyList())
                else -> {
                    val listOfItems = children.mapNotNull { it.getValue(type) }
                    onSuccess(listOfItems)
                }
            }
        }
        catch (e: Exception)
        {
            onError(e.message)
        }
    }

    override fun onCancelled(error: DatabaseError?) { onError(error?.message) }
}