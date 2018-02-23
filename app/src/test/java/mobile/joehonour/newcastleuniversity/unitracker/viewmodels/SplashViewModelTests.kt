package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.`when` as _when

class SplashViewModelTests {

    @Test
    fun canGetUserLoggedInTrue() {
        val authProviderMock = Mockito.mock(IProvideAuthentication::class.java)
        _when(authProviderMock.userLoggedIn).then { true }

        val viewModel = SplashViewModel(authProviderMock)
        assertTrue(viewModel.userLoggedIn)
    }

    @Test
    fun canGetUserLoggedInFalse() {
        val authProviderMock = Mockito.mock(IProvideAuthentication::class.java)
        _when(authProviderMock.userLoggedIn).then { false }

        val viewModel = SplashViewModel(authProviderMock)
        assertFalse(viewModel.userLoggedIn)
    }
}