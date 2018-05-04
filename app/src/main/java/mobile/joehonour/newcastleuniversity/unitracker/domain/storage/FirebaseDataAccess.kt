package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

import com.google.firebase.database.DatabaseReference
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.FirebaseDataAccessMultipleValueEventListener
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.FirebaseDataAccessSingleValueEventListener

class FirebaseDataAccess(private val databaseReference: DatabaseReference) : IProvideDataAccess
{
    override fun <T : Any> readItemFromDatabase(
            key: String,
            type: Class<T>,
            onError: (String?) -> Unit,
            onSuccess: (T) -> Unit)
    {
        val databaseListener = FirebaseDataAccessSingleValueEventListener(onError, onSuccess, type)
        databaseReference.child(key).addValueEventListener(databaseListener)
    }

    override fun <T : Any> readCollectionFromDatabase(
            key: String,
            itemType: Class<T>,
            onError: (String?) -> Unit, onSuccess: (List<T>) -> Unit)
    {
        val databaseListener = FirebaseDataAccessMultipleValueEventListener(onError, onSuccess, itemType)
        databaseReference.child(key).addValueEventListener(databaseListener)
    }
}

