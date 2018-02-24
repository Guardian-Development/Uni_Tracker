package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SplashViewModelTests {

    @Test
    fun canGetUserLoggedInTrue() {
        val authProviderMock = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
        }

        val viewModel = SplashViewModel(authProviderMock)
        assertTrue(viewModel.userLoggedIn)
    }

    @Test
    fun canGetUserLoggedInFalse() {
        val authProviderMock = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val viewModel = SplashViewModel(authProviderMock)
        assertFalse(viewModel.userLoggedIn)
    }
}