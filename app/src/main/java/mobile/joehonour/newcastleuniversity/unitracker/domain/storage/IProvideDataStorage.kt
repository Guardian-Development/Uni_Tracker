package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

/**
 * Provides the ability to store items in a database.
 */
interface IProvideDataStorage
{
    /**
     * Provides the ability to add an item to the database under the given key.
     *
     * @param key the key you wish to save the item under in the database.
     * @param itemToAdd the item you wish to add under the key in the database.
     * @param onError is executed when an error occurs when attempting to save the item under the key.
     * @param onSuccess is executed when the successful storage of the item is achieved.
     */
    fun <T> addItemToDatabase(
            key: String,
            itemToAdd: T,
            onError: (String?) -> Unit,
            onSuccess: () -> Unit)
}

