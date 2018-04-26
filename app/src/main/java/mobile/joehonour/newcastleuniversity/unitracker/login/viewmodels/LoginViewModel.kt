package mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels

import android.arch.lifecycle.ViewModel
import com.facebook.AccessToken
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

class LoginViewModel(private val authProvider: IProvideAuthentication,
                     private val userStateQuery: IQueryUserState) : ViewModel()
{
    fun userHasCompletedSetup(result: (Boolean) -> Unit) =
        userStateQuery.userHasCompletedConfiguration(result)

    fun authenticateWithTwitterSession(
            session: TwitterSession,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        authProvider.authenticateWithTwitterSession(
                session.authToken.token,
                session.authToken.secret,
                callback)
    }

    fun authenticateWithFacebookSession(
            accessToken: AccessToken,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        authProvider.authenticateWithFacebookSession(accessToken.token, callback)
    }

    fun authenticateWithGoogleSession(
            idToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        authProvider.authenticateWithGoogleSession(idToken, callback)
    }
}