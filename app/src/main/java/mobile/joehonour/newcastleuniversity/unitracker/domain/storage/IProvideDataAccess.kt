package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

interface IProvideDataAccess
{
    fun <T : Any> readItemFromDatabase(key: String,
                                 type: Class<T>,
                                 onError: (String?) -> Unit,
                                 onSuccess: (T) -> Unit)

    fun <T : Any> readCollectionFromDatabase(key: String,
                                       itemType: Class<T>,
                                       onError: (String?) -> Unit,
                                       onSuccess: (List<T>) -> Unit)
}