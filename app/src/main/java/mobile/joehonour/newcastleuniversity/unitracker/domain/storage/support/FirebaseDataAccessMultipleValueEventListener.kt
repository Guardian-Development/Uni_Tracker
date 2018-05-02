package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * Provides a Google Firebase data event listener, that can listen for database records that contain
 * lists of results. This class then deserialize's the record into a list of elements and executes
 * the onSuccess function, passing it the records.
 *
 * @param onError executed if an error occurs when reading an item from the database.
 * @param onSuccess executed when the elements within the database were successfully read.
 * @param type the type of each individual item in the list of elements you wish to read.
 */
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