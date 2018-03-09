package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.arch.lifecycle.ViewModel
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

class LoginViewModel(private val authProvider: IProvideAuthentication,
                     private val userStateQuery: IQueryUserState) : ViewModel()
{
    fun userHasCompletedSetup(result: (Boolean) -> Unit) =
        userStateQuery.userHasCompletedInitialSetup(result)

    val userLoggedIn: Boolean by lazy { authProvider.userLoggedIn }

    fun authenticateWithTwitterSession(
            session: TwitterSession,
            callback: (status: Boolean, errorMessage: String?) -> Unit) {

        authProvider.authenticateWithTwitterSession(
                session.authToken.token,
                session.authToken.secret,
                callback)
    }
}