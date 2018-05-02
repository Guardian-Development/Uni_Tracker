package mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels

import android.arch.lifecycle.ViewModel
import com.facebook.AccessToken
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

/**
 * The view model is responsible for presenting information to the login view.
 *
 * @param authProvider provides functionality to deem if the user is currently logged in.
 * @param userStateQuery provides functionality to query the users information.
 */
class LoginViewModel(private val authProvider: IProvideAuthentication,
                     private val userStateQuery: IQueryUserState) : ViewModel()
{
    /**
     * Responsible for determining whether the user has completed the setup stages of the app.
     *
     * @param result is executed with true if the user has completed setup, else false.
     */
    fun userHasCompletedSetup(result: (Boolean) -> Unit) =
        userStateQuery.userHasCompletedConfiguration(result)

    /**
     * Responsible for authenticating a user with the Twitter session token.
     *
     * @param session the users Twitter session token.
     * @param callback is executed being passed the result of the authentication attempt.
     */
    fun authenticateWithTwitterSession(
            session: TwitterSession,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        authProvider.authenticateWithTwitterSession(
                session.authToken.token,
                session.authToken.secret,
                callback)
    }

    /**
     * Responsible for authenticating a user with the Facebook access token.
     *
     * @param accessToken the users Facebook access token.
     * @param callback is executed being passed the result of the authentication attempt.
     */
    fun authenticateWithFacebookSession(
            accessToken: AccessToken,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        authProvider.authenticateWithFacebookSession(accessToken.token, callback)
    }

    /**
     * Responsible for authenticating a user with the Google idToken.
     *
     * @param idToken the users Google idToken.
     * @param callback is executed being passed the result of the authentication attempt.
     */
    fun authenticateWithGoogleSession(
            idToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        authProvider.authenticateWithGoogleSession(idToken, callback)
    }
}