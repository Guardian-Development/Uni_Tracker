package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess

class UserStateQuery(private val dataAccess: IProvideDataAccess,
                     private val authProvider: IProvideAuthentication) : IQueryUserState
{
    override fun userHasCompletedConfiguration(result: (Boolean) -> Unit)
    {
        getUserConfiguration({ result(false) }, { result(true) })
    }

    override fun getUserConfiguration(onError: (String?) -> Unit, onSuccess: (Configuration) -> Unit)
    {
        when(authProvider.userLoggedIn) {
            false -> onError("user not logged in")
            true -> dataAccess.readItemFromDatabase(
                    authProvider.userUniqueId!! + "/configuration",
                    Configuration::class.java,
                    onError,
                    onSuccess)
        }
    }
}