package mobile.joehonour.newcastleuniversity.unitracker.login

import com.facebook.AccessToken
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.LoginViewModel
import org.junit.Test
import org.mockito.Mockito

class LoginViewModelTests
{
    @Test
    fun canAuthenticateWithTwitterSuccess()
    {
        val callback = mock<(Boolean, String?) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on {
                authenticateWithTwitterSession("token", "secret", callback)
            } doAnswer { callback.invoke(true, null) }
        }

        val authToken = Mockito.spy(TwitterAuthToken("token", "secret"))

        val twitterSession = mock<TwitterSession> {
            on { getAuthToken() } doReturn authToken
        }

        val viewModel = LoginViewModel(authProvider, mock())

        viewModel.authenticateWithTwitterSession(twitterSession, callback)
        verify(callback).invoke(true, null)
    }

    @Test
    fun canAuthenticateWithTwitterFailure()
    {
        val callback = mock<(Boolean, String?) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on {
                authenticateWithTwitterSession("token", "secret", callback)
            } doAnswer { callback.invoke(false, "failed twitter") }
        }

        val authToken = Mockito.spy(TwitterAuthToken("token", "secret"))

        val twitterSession = mock<TwitterSession> {
            on { getAuthToken() } doReturn authToken
        }

        val viewModel = LoginViewModel(authProvider, mock())

        viewModel.authenticateWithTwitterSession(twitterSession, callback)
        verify(callback).invoke(false, "failed twitter")
    }

    @Test
    fun canAuthenticateWithFacebookSuccess()
    {
        val callback = mock<(Boolean, String?) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on {
                authenticateWithFacebookSession("token", callback)
            } doAnswer { callback.invoke(true, null) }
        }

        val accessToken = AccessToken("token", "appId", "userId", null, null, null, null, null)

        val viewModel = LoginViewModel(authProvider, mock())

        viewModel.authenticateWithFacebookSession(accessToken, callback)
        verify(callback).invoke(true, null)
    }

    @Test
    fun canAuthenticateWithFacebookFailure()
    {
        val callback = mock<(Boolean, String?) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on {
                authenticateWithFacebookSession("token", callback)
            } doAnswer { callback.invoke(false, "failed facebook") }
        }

        val accessToken = AccessToken("token", "appId", "userId", null, null, null, null, null)
        val viewModel = LoginViewModel(authProvider, mock())

        viewModel.authenticateWithFacebookSession(accessToken, callback)
        verify(callback).invoke(false, "failed facebook")
    }

    @Test
    fun canDistinguishUserCompletedSetupTrue()
    {
        val callback = mock<(Boolean) -> Unit>()

        val userStateQuery = mock<IQueryUserState> {
            on { userHasCompletedConfiguration(callback) } doAnswer { callback.invoke(true) }
        }

        val viewModel = LoginViewModel(mock(), userStateQuery)

        viewModel.userHasCompletedSetup(callback)
        verify(callback).invoke(true)
    }

    @Test
    fun canDistinguishUserCompletedSetupFalse()
    {
        val callback = mock<(Boolean) -> Unit>()

        val userStateQuery = mock<IQueryUserState> {
            on { userHasCompletedConfiguration(callback) } doAnswer { callback.invoke(false) }
        }

        val viewModel = LoginViewModel(mock(), userStateQuery)

        viewModel.userHasCompletedSetup(callback)
        verify(callback).invoke(false)
    }
}