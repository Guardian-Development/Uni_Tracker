package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

import com.google.firebase.database.DatabaseReference

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