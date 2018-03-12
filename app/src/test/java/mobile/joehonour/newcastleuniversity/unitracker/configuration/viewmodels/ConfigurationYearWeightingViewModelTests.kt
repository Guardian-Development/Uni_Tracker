package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationYearWeightingViewModelTester.Companion.configurationYearWeightingViewModelTester
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers


class ConfigurationYearWeightingViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()

    @Test
    fun completedWeightingsForAllYearsFalseWhenNoConfigurationData()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator>()

        configurationYearWeightingViewModelTester(validator)
                .performActions {
                    configurationData = null
                }
                .assertCompletedWeightingsForAllYearsFalse()
    }

    @Test
    fun completedWeightingsForAllYearsFalseWhenNotCompleted()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(ArgumentMatchers.any()) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 3)
                .performActions {
                    bindModelForCurrentYear { it.value = 10 }
                    finishEditingCurrentYear()
                    bindModelForCurrentYear { it.value = 10 }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsFalse()
    }

    @Test
    fun completedWeightingsForAllYearsTrueWhenCompleted()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(ArgumentMatchers.any()) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 2)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                    bindModelForCurrentYear { it.value = 20 }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsTrue()
    }

    @Test
    fun bindModelForCurrentYearCallsBindCorrectly()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator>()
        val bindingFunction = mock<(MutableLiveData<Int>) -> Unit>()

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 5)
                .performActions {
                    bindModelForCurrentYear(bindingFunction)
                }

        verify(bindingFunction).invoke(any())
    }

    @Test
    fun bindModelForCurrentYearNoSetupDataThrowsException()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator>()

        thrown.expect(IllegalStateException::class.java)

        configurationYearWeightingViewModelTester(validator)
                .performActions {
                    configurationData = null
                    bindModelForCurrentYear {}
                }
    }

    @Test
    fun finishEditingCurrentYearOnLastYearSetsCompletedWeightingsForAllYearsTrue()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(ArgumentMatchers.any()) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 2)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsFalse()
                .performActions {
                    bindModelForCurrentYear { it.value = 20 }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsTrue()
    }

    @Test
    fun validDataEnteredForCurrentYearWeightingReturnsTrueCorrectly()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(20) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 3)
                .performActions {
                    bindModelForCurrentYear { it.value = 20 }
                }
                .assertValidDataEnteredForCurrentYearWeightingTrue()
    }

    @Test
    fun validDataEnteredForCurrentYearWeightingReturnsFalseCorrectly()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(78) } doReturn false
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear { it.value = 78 }
                }
                .assertValidDataEnteredForCurrentYearWeightingFalse()
    }

    @Test
    fun userLoggedInSaveConfigurationExecutesSuccess()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(any()) } doReturn true
        }
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }
        val onSuccess = mock<(() -> Unit)> {
            on { invoke() } doAnswer {}
        }
        val onError = mock<((String?) -> Unit)>()
        val storageProvider = mock<IProvideDataStorage> {
            on { addItemToDatabase<Configuration>(
                    eq("id" + "/configuration"),
                    any(),
                    eq(onError),
                    eq(onSuccess))} doAnswer { onSuccess() }
        }

        configurationYearWeightingViewModelTester(validator, storageProvider, authProvider)
                .withConfigurationDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                    saveConfiguration(onError, onSuccess)
                }

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun userNotLoggedInSaveConfigurationExecutesFailure()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(any()) } doReturn true
        }
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
            on { userUniqueId } doReturn "id"
        }
        val onSuccess = mock<(() -> Unit)> {
            on { invoke() } doAnswer {}
        }
        val onError = mock<((String?) -> Unit)>()
        val storageProvider = mock<IProvideDataStorage> {
            on { addItemToDatabase<Configuration>(
                    eq("id" + "/configuration"),
                    any(),
                    eq(onError),
                    eq(onSuccess))} doAnswer { onSuccess() }
        }

        configurationYearWeightingViewModelTester(validator, storageProvider, authProvider)
                .withConfigurationDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                    saveConfiguration(onError, onSuccess)
                }

        verify(onError).invoke(any())
        verifyZeroInteractions(onSuccess)
    }
}