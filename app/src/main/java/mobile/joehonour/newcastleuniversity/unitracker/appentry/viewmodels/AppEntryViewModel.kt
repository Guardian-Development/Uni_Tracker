package mobile.joehonour.newcastleuniversity.unitracker.appentry.viewmodels

import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

/**
 * The view model is responsible for presenting information to the app entry view.
 *
 * @param authProvider provides functionality to deem if the user is currently logged in.
 * @param userStateQuery provides functionality to query the users information.
 */
class AppEntryViewModel(private val authProvider: IProvideAuthentication,
                        private val userStateQuery: IQueryUserState) : ViewModel()
{
    /**
     * Responsible for querying the user state in order to determine is a user has completed the
     * initial setup of the app.
     *
     * @param result is executed with the result of whether the use has completed setup or not.
     */
    fun userHasCompletedSetup(result: (Boolean) -> Unit) =
            userStateQuery.userHasCompletedConfiguration(result)

    /**
     * Responsible for querying the users authentication status in order to find out if they are
     * logged in or not.
     */
    val userLoggedIn: Boolean by lazy { authProvider.userLoggedIn }
}
