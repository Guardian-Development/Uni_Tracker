package mobile.joehonour.newcastleuniversity.unitracker.appentry.viewmodels

import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

class AppEntryViewModel(private val authProvider: IProvideAuthentication,
                        private val userStateQuery: IQueryUserState) : ViewModel()
{
    fun userHasCompletedSetup(result: (Boolean) -> Unit) =
            userStateQuery.userHasCompletedConfiguration(result)

    val userLoggedIn: Boolean by lazy { authProvider.userLoggedIn }
}
