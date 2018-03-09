package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

import com.google.firebase.database.DatabaseReference
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.FirebaseDataAccessValueEventListener

class FirebaseDataAccess(private val databaseReference: DatabaseReference) : IProvideDataAccess {

    override fun <T> readItemFromDatabase(
            key: String,
            type: Class<T>,
            onError: (String?) -> Unit,
            onSuccess: (T) -> Unit)
    {
        val databaseListener = FirebaseDataAccessValueEventListener(onError, onSuccess, type)
        databaseReference.child(key).addListenerForSingleValueEvent(databaseListener)
    }
}

