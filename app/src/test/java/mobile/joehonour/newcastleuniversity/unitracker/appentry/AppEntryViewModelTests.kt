package mobile.joehonour.newcastleuniversity.unitracker.appentry

import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import mobile.joehonour.newcastleuniversity.unitracker.appentry.viewmodels.AppEntryViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import org.junit.Test

class AppEntryViewModelTests
{
    @Test
    fun canDistinguishUserCompletedSetupTrue()
    {
        val callback = mock<(Boolean) -> Unit>()

        val userStateQuery = mock<IQueryUserState> {
            on { userHasCompletedConfiguration(callback) } doAnswer { callback.invoke(true) }
        }

        val viewModel = AppEntryViewModel(mock(), userStateQuery)

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

        val viewModel = AppEntryViewModel(mock(), userStateQuery)

        viewModel.userHasCompletedSetup(callback)
        verify(callback).invoke(false)
    }

    @Test
    fun canShowUserLoggedInTrue()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
        }

        val viewModel = AppEntryViewModel(authProvider, mock())
        FieldAssert(true).doAssert(viewModel.userLoggedIn)
    }

    @Test
    fun canShowUserLoggedInFalse()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val viewModel = AppEntryViewModel(authProvider, mock())
        FieldAssert(false).doAssert(viewModel.userLoggedIn)
    }
}
