package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

import com.google.firebase.database.DatabaseReference
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.FirebaseDataAccessMultipleValueEventListener
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.FirebaseDataAccessSingleValueEventListener

class FirebaseDataReadAccess(private val databaseReference: DatabaseReference)
    : IProvideDataSingleReadAccess, IProvideDataConstantListeningReadAccess
{
    override fun <T : Any> readItemFromDatabaseAndListenForChanges(
            key: String,
            type: Class<T>,
            onError: (String?) -> Unit,
            onSuccess: (T) -> Unit)
    {
        val databaseListener = FirebaseDataAccessSingleValueEventListener(onError, onSuccess, type)
        databaseReference.child(key).addValueEventListener(databaseListener)
    }

    override fun <T : Any> readCollectionFromDatabaseAndListenForChanges(
            key: String,
            itemType: Class<T>,
            onError: (String?) -> Unit, onSuccess: (List<T>) -> Unit)
    {
        val databaseListener = FirebaseDataAccessMultipleValueEventListener(onError, onSuccess, itemType)
        databaseReference.child(key).addValueEventListener(databaseListener)
    }

    override fun <T : Any> readItemFromDatabase(
            key: String,
            type: Class<T>,
            onError: (String?) -> Unit,
            onSuccess: (T) -> Unit)
    {
        val databaseListener = FirebaseDataAccessSingleValueEventListener(onError, onSuccess, type)
        databaseReference.child(key).addListenerForSingleValueEvent(databaseListener)
    }

    override fun <T : Any> readCollectionFromDatabase(
            key: String,
            itemType: Class<T>,
            onError: (String?) -> Unit, onSuccess: (List<T>) -> Unit)
    {
        val databaseListener = FirebaseDataAccessMultipleValueEventListener(onError, onSuccess, itemType)
        databaseReference.child(key).addListenerForSingleValueEvent(databaseListener)
    }
}

