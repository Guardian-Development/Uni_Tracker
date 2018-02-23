package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import com.twitter.sdk.android.core.TwitterAuthToken
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as _when

class LoginViewModelTests {

    @Test
    fun canAuthenticateWithTwitterSuccess() {
        val authProvider = Mockito.mock(IProvideAuthentication::class.java)
        val authToken = Mockito.spy(TwitterAuthToken("token", "secret"))
        val viewModel = LoginViewModel(authProvider)
        val twitterSession = Mockito.mock(TwitterSession::class.java)

        _when(twitterSession.authToken).then { authToken }
        //_when(authProvider.authenticateWithTwitterSession())
        //https://github.com/nhaarman/mockito-kotlin/issues/145
        //val mock = mock<() -> Unit>()
        //doSomething(mock)
        //verify(mock).invoke()

        viewModel.authenticateWithTwitterSession(twitterSession) {
            status, errorMessage ->
                assertTrue(status)
                assertNull(errorMessage)
        }

        //TODO: implement
        fail()
    }

    @Test
    fun canAuthenticateWithTwitterFailure() {
        fail()
    }
}