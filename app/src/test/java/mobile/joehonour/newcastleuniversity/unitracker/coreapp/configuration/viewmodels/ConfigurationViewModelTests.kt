package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ConfigurationViewModelTests
{
    @Rule @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canGetConfigurationSuccess()
    {
        val weightings = listOf(
                ConfigurationYearWeighting(1, 20, 120),
                ConfigurationYearWeighting(2, 40, 0),
                ConfigurationYearWeighting(3, 60, 120))

        val configuration = Configuration(
                "Leeds",
                2014,
                4,
                75,
                240,
                weightings)

        val query = mock<IQueryUserState> {
            on { getUserConfiguration(any(), any()) } doAnswer {
                val onSuccess = it.arguments[1] as ((Configuration) -> Unit)
                onSuccess(configuration)
            }
        }

        val viewModel = ConfigurationViewModel(query, mock())

        val yearWeightingAssert = UnorderedListAssert<Int, ConfigurationYearWeightingModel>({ it.year })
        yearWeightingAssert.asserts[1] =
                ConfigurationYearWeightingModelAssert(FieldAssert(1), FieldAssert(20))
        yearWeightingAssert.asserts[2] =
                ConfigurationYearWeightingModelAssert(FieldAssert(2), FieldAssert(40))
        yearWeightingAssert.asserts[3] =
                ConfigurationYearWeightingModelAssert(FieldAssert(3), FieldAssert(60))

        val configurationAssert = ConfigurationModelAssert(
                FieldAssert("Leeds"),
                FieldAssert(2014),
                FieldAssert(4),
                FieldAssert(75),
                FieldAssert(240),
                yearWeightingAssert)

        configurationAssert.doAssert(viewModel.configuration.value!!)
    }

    @Test
    fun canGetConfigurationFailure()
    {
        val query = mock<IQueryUserState> {
            on { getUserConfiguration(any(), any()) } doAnswer {
                val onError = it.arguments[0] as ((String?) -> Unit)
                onError("error getting configuration")
            }
        }

        val viewModel = ConfigurationViewModel(query, mock())
        assertNull(viewModel.configuration.value)
    }

    @Test
    fun canLogoutSuccess()
    {
        val onSuccess = mock<(() -> Unit)>()
        val onError = mock<((String?) -> Unit)>()

        val authProvider = mock<IProvideAuthentication> {
            on { logout(any()) } doAnswer {
                val callback = it.arguments[0] as ((Boolean, String?) -> Unit)
                callback.invoke(true, null)
            }
        }

        val viewModel = ConfigurationViewModel(mock(), authProvider)

        viewModel.logoutOfApplication(onError, onSuccess)

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun canLogoutFailure()
    {
        val onSuccess = mock<(() -> Unit)>()
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                val error = it.arguments[0] as String?
                FieldAssert<String?>("logout error").doAssert(error)
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { logout(any()) } doAnswer {
                val callback = it.arguments[0] as ((Boolean, String?) -> Unit)
                callback.invoke(false, "logout error")
            }
        }

        val viewModel = ConfigurationViewModel(mock(), authProvider)

        viewModel.logoutOfApplication(onError, onSuccess)

        verify(onError).invoke("logout error")
        verifyZeroInteractions(onSuccess)
    }
}