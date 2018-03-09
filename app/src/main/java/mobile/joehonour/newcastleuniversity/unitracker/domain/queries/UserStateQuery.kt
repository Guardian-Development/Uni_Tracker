package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.AppConfiguration
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess

class UserStateQuery(private val dataAccess: IProvideDataAccess,
                     private val authProvider: IProvideAuthentication) : IQueryUserState
{
    override fun userHasCompletedInitialSetup(result: (Boolean) -> Unit)
    {
        when(authProvider.userLoggedIn) {
            false -> result(false)
            true -> dataAccess.readItemFromDatabase(
                    authProvider.userUniqueId!! + "/configuration",
                    AppConfiguration::class.java,
                    { result(false) },
                    { result(true) })
        }
    }
}