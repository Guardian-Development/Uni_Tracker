package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationYearWeightingViewModelTester.Companion.configurationYearWeightingViewModelTester
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule


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
            on { validate(any(), any()) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 3)
                .performActions {
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 10
                        currentYearCreditsCompleted.value = 50
                    }
                    finishEditingCurrentYear()
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 10
                        currentYearCreditsCompleted.value = 50
                    }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsFalse()
    }

    @Test
    fun completedWeightingsForAllYearsTrueWhenCompleted()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(any(), any()) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 2)
                .performActions {
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 50
                        currentYearCreditsCompleted.value = 120
                    }
                    finishEditingCurrentYear()
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 20
                        currentYearCreditsCompleted.value = 50
                    }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsTrue()
    }

    @Test
    fun bindModelForCurrentYearCallsBindCorrectly()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator>()
        val bindingFunction = mock<(ConfigurationYearWeightingViewModel) -> Unit>()

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
            on { validate(any(), any()) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 2)
                .performActions {
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 50
                        currentYearCreditsCompleted.value = 120
                    }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsFalse()
                .performActions {
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 20
                        currentYearCreditsCompleted.value = 10
                    }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsTrue()
    }

    @Test
    fun validDataEnteredForCurrentYearWeightingReturnsTrueCorrectly()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(20, 120) } doReturn true
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 3)
                .performActions {
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 20
                        currentYearCreditsCompleted.value = 120
                    }
                }
                .assertValidDataEnteredForCurrentYearWeightingTrue()
    }

    @Test
    fun validDataEnteredForCurrentYearWeightingReturnsFalseCorrectly()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(78, 240) } doReturn false
        }

        configurationYearWeightingViewModelTester(validator)
                .withConfigurationDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 78
                        currentYearCreditsCompleted.value = 240
                    }
                }
                .assertValidDataEnteredForCurrentYearWeightingFalse()
    }

    @Test
    fun userLoggedInSaveConfigurationExecutesSuccess()
    {
        val validator = mock<ConfigurationYearWeightingModelValidator> {
            on { validate(any(), any()) } doReturn true
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
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 50
                        currentYearCreditsCompleted.value = 120
                    }
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
            on { validate(any(), any()) } doReturn true
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
                    bindModelForCurrentYear {
                        currentYearWeighting.value = 50
                        currentYearCreditsCompleted.value = 30
                    }
                    finishEditingCurrentYear()
                    saveConfiguration(onError, onSuccess)
                }

        verify(onError).invoke(any())
        verifyZeroInteractions(onSuccess)
    }
}