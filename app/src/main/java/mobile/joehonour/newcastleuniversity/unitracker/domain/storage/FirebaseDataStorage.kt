package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

import com.google.firebase.database.DatabaseReference

/**
 * Provides storage of items within Google Firebase.
 *
 * @param databaseReference the Google Firebase Database you wish to use when storing items.
 */
class FirebaseDataStorage(private val databaseReference: DatabaseReference) : IProvideDataStorage
{
    override fun <T> addItemToDatabase(
            key: String,
            itemToAdd: T,
            onError: (String?) -> Unit,
            onSuccess: () -> Unit)
    {
        databaseReference.child(key).setValue(itemToAdd).addOnCompleteListener {
            when(it.isSuccessful) {
                true -> onSuccess()
                false -> onError(it.exception?.message)
            }
        }
    }
}