package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FirebaseDataAccessValueEventListener<T>(
        private val onError: (String?) -> Unit,
        private val onSuccess: (T) -> Unit,
        private val type: Class<T>) : ValueEventListener
{
    override fun onDataChange(data: DataSnapshot?) {
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