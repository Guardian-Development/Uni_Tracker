package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.arch.lifecycle.ViewModel
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication

class LoginViewModel(private val authProvider: IProvideAuthentication) : ViewModel() {

    val userHasCompletedSetup: Boolean = false

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