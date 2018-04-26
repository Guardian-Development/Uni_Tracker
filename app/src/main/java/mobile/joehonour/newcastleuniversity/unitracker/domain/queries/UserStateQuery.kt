package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

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
                    DataLocationKeys.studentConfigurationLocation(authProvider.userUniqueId!!),
                    Configuration::class.java,
                    onError,
                    onSuccess)
        }
    }

    override fun getUserModules(onError: (String?) -> Unit, onSuccess: (List<Module>) -> Unit)
    {
        when(authProvider.userLoggedIn) {
            false -> onError("User not logged in")
            true -> dataAccess.readCollectionFromDatabase(
                    DataLocationKeys.studentModulesLocation(authProvider.userUniqueId!!),
                    Module::class.java,
                    onError,
                    onSuccess
            )
        }
    }
}