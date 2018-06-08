package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

interface IProvideDataConstantListeningReadAccess
{
    fun <T : Any> readItemFromDatabaseAndListenForChanges(key: String,
                                       type: Class<T>,
                                       onError: (String?) -> Unit,
                                       onSuccess: (T) -> Unit)

    fun <T : Any> readCollectionFromDatabaseAndListenForChanges(key: String,
                                             itemType: Class<T>,
                                             onError: (String?) -> Unit,
                                             onSuccess: (List<T>) -> Unit)
}