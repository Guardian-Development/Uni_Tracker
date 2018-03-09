package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

interface IProvideDataAccess
{
    fun <T> readItemFromDatabase(key: String,
                                 type: Class<T>,
                                 onError: (String?) -> Unit,
                                 onSuccess: (T) -> Unit)
}