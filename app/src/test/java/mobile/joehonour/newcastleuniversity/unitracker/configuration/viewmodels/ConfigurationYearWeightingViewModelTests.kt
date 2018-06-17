package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ConfigurationYearWeightingViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canSaveConfigurationSuccess()
    {
        val onSuccess = mock<(() -> Unit)>()
        val onError = mock<((String?) -> Unit)>()

        val dataStorage = mock<IProvideDataStorage> {
            on { addItemToDatabase(eq("userId/configuration/"), any<Configuration>(), any(), any()) } doAnswer {
                val configuration = it.arguments[1] as Configuration
                val yearWeightingAssert = UnorderedListAssert<Int, ConfigurationYearWeighting>({ it.year })
                yearWeightingAssert.asserts[1] = ConfigurationYearWeightingAssert(FieldAssert(1), FieldAssert(20), FieldAssert(50))
                yearWeightingAssert.asserts[2] = ConfigurationYearWeightingAssert(FieldAssert(2), FieldAssert(60), FieldAssert(100))
                yearWeightingAssert.asserts[3] = ConfigurationYearWeightingAssert(FieldAssert(3), FieldAssert(20), FieldAssert(100))
                ConfigurationAssert(
                        FieldAssert("Newcastle University"),
                        FieldAssert(2014),
                        FieldAssert(3),
                        FieldAssert(75),
                        FieldAssert(250),
                        yearWeightingAssert).doAssert(configuration)
                onSuccess()
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "userId"
        }

        val viewModel = ConfigurationYearWeightingViewModel(dataStorage, authProvider)
        viewModel.configurationData = ConfigurationDataModel("Newcastle University", 2014, 75)
        viewModel.yearWeightings.value!!.add(ConfigurationYearWeightingModel(1, 20, 50))
        viewModel.yearWeightings.value!!.add(ConfigurationYearWeightingModel(2, 60, 100))
        viewModel.yearWeightings.value!!.add(ConfigurationYearWeightingModel(3, 20, 100))
        viewModel.saveConfiguration(onError, onSuccess)

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun canSaveConfigurationUserNotLoggedInFails()
    {
        val onSuccess = mock<(() -> Unit)>()
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer { FieldAssert("User not logged in").doAssert(it.arguments[0] as String)}
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val viewModel = ConfigurationYearWeightingViewModel(mock(), authProvider)
        viewModel.configurationData = ConfigurationDataModel("Newcastle University", 2014, 75)
        viewModel.saveConfiguration(onError, onSuccess)

        verify(onError).invoke("User not logged in")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun canSaveConfigurationConfigurationDataNullFails()
    {
        val onSuccess = mock<(() -> Unit)>()
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Configuration Data can not be null").doAssert(it.arguments[0] as String)
            }
        }

        val viewModel = ConfigurationYearWeightingViewModel(mock(), mock())
        viewModel.saveConfiguration(onError, onSuccess)

        verify(onError).invoke("Configuration Data can not be null")
        verifyZeroInteractions(onSuccess)
    }
}

