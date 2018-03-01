package mobile.joehonour.newcastleuniversity.unitracker.domain.storage

interface IProvideDataStorage
{
    fun <T> addItemToDatabase(
            key: String,
            itemToAdd: T,
            onError: (String?) -> Unit,
            onSuccess: () -> Unit)
}

