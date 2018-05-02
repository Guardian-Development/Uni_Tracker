package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

/**
 * Provides the ability to read items from a database.
 */
interface IProvideDataAccess
{
    /**
     * Provides the ability to read a single item from the database at the given key.
     *
     * @param key the database key to read from. (document database)
     * @param type the type of class you wish to deserialize under the key.
     * @param onError is executed if an error occurs when reading the item under the key.
     * @param onSuccess is executed on successful read of the item, being passed the resulting item.
     */
    fun <T : Any> readItemFromDatabase(key: String,
                                 type: Class<T>,
                                 onError: (String?) -> Unit,
                                 onSuccess: (T) -> Unit)

    /**
     * Provides the ability to read a list of items from the database at the given key.
     *
     * @param key the database key to read from. (document database)
     * @param onError is executed if an error occurs when reading the item under the key.
     * @param onSuccess is executed on successful read of the list of items,
     * being passed the resulting list of items.
     */
    fun <T : Any> readCollectionFromDatabase(key: String,
                                       itemType: Class<T>,
                                       onError: (String?) -> Unit,
                                       onSuccess: (List<T>) -> Unit)
}