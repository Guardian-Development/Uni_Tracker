package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * Provides a Google Firebase data event listener, that can listen for database records that contain
 * a single result. This class then deserialize's the record and execute the onSuccess function,
 * passing it the record.
 *
 * @param onError executed if an error occurs when reading an item from the database.
 * @param onSuccess executed when the element within the database was successfully read.
 * @param type the type of the item you wish to read.
 */
class FirebaseDataAccessSingleValueEventListener<T : Any>(
        private val onError: (String?) -> Unit,
        private val onSuccess: (T) -> Unit,
        private val type: Class<T>) : ValueEventListener
{
    override fun onDataChange(data: DataSnapshot?)
    {
        try {
            val item = data?.getValue(type)
            when(item) {
                null -> onError("no item exists in the database")
                else -> onSuccess(item)
            }
        }
        catch (e: Exception)
        {
            onError(e.message)
        }
    }

    override fun onCancelled(error: DatabaseError?) = onError.invoke(error?.message)
}

