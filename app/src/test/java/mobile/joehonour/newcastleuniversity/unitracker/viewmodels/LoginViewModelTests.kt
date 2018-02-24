package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import org.junit.Test
import org.mockito.Mockito

class LoginViewModelTests {

    @Test
    fun canAuthenticateWithTwitterSuccess() {
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

        val viewModel = LoginViewModel(authProvider)

        viewModel.authenticateWithTwitterSession(twitterSession, callback)
        verify(callback).invoke(true, null)
    }

    @Test
    fun canAuthenticateWithTwitterFailure() {

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

        val viewModel = LoginViewModel(authProvider)

        viewModel.authenticateWithTwitterSession(twitterSession, callback)
        verify(callback).invoke(false, "failed twitter")
    }
}