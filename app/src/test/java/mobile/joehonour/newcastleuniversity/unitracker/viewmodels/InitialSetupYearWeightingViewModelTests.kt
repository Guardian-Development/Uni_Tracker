package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.AppConfiguration
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.InitialSetupYearWeightingViewModelTester.Companion.initialSetupYearWeightingViewModelTester
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers


class InitialSetupYearWeightingViewModelTests {

    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()

    @Test
    fun completedWeightingsForAllYearsFalseWhenNoInitialSetupData() {
        val validator = mock<InitialSetupYearWeightingModelValidator>()

        initialSetupYearWeightingViewModelTester(validator)
                .performActions {
                    initialSetupData = null
                }
                .assertCompletedWeightingsForAllYearsFalse()
    }

    @Test
    fun completedWeightingsForAllYearsFalseWhenNotCompleted() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
            on { validate(ArgumentMatchers.any()) } doReturn true
        }

        initialSetupYearWeightingViewModelTester(validator)
                .withInitialSetupDataModel(courseLength = 3)
                .performActions {
                    bindModelForCurrentYear { it.value = 10 }
                    finishEditingCurrentYear()
                    bindModelForCurrentYear { it.value = 10 }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsFalse()
    }

    @Test
    fun completedWeightingsForAllYearsTrueWhenCompleted() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
            on { validate(ArgumentMatchers.any()) } doReturn true
        }

        initialSetupYearWeightingViewModelTester(validator)
                .withInitialSetupDataModel(courseLength = 2)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                    bindModelForCurrentYear { it.value = 20 }
                    finishEditingCurrentYear()
                }
                .assertCompletedWeightingsForAllYearsTrue()
    }

    @Test
    fun bindModelForCurrentYearCallsBindCorrectly() {
        val validator = mock<InitialSetupYearWeightingModelValidator>()
        val bindingFunction = mock<(MutableLiveData<Int>) -> Unit>()

        initialSetupYearWeightingViewModelTester(validator)
                .withInitialSetupDataModel(courseLength = 5)
                .performActions {
                    bindModelForCurrentYear(bindingFunction)
                }

        verify(bindingFunction).invoke(any())
    }

    @Test
    fun bindModelForCurrentYearNoSetupDataThrowsException() {
        val validator = mock<InitialSetupYearWeightingModelValidator>()

        thrown.expect(IllegalStateException::class.java)

        initialSetupYearWeightingViewModelTester(validator)
                .performActions {
                    initialSetupData = null
                    bindModelForCurrentYear {}
                }
    }

    @Test
    fun finishEditingCurrentYearOnLastYearSetsCompletedWeightingsForAllYearsTrue() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
            on { validate(ArgumentMatchers.any()) } doReturn true
        }

        initialSetupYearWeightingViewModelTester(validator)
                .withInitialSetupDataModel(courseLength = 2)
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
    fun validDataEnteredForCurrentYearWeightingReturnsTrueCorrectly() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
            on { validate(20) } doReturn true
        }

        initialSetupYearWeightingViewModelTester(validator)
                .withInitialSetupDataModel(courseLength = 3)
                .performActions {
                    bindModelForCurrentYear { it.value = 20 }
                }
                .assertValidDataEnteredForCurrentYearWeightingTrue()
    }

    @Test
    fun validDataEnteredForCurrentYearWeightingReturnsFalseCorrectly() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
            on { validate(78) } doReturn false
        }

        initialSetupYearWeightingViewModelTester(validator)
                .withInitialSetupDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear { it.value = 78 }
                }
                .assertValidDataEnteredForCurrentYearWeightingFalse()
    }

    @Test
    fun userLoggedInSaveInitialDataExecutesSuccess() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
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
            on { addItemToDatabase<AppConfiguration>(
                    eq("id"),
                    any(),
                    eq(onError),
                    eq(onSuccess))} doAnswer { onSuccess() }
        }

        initialSetupYearWeightingViewModelTester(validator, storageProvider, authProvider)
                .withInitialSetupDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                    saveInitialSetup(onError, onSuccess)
                }

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun userNotLoggedInSaveInitialDataExecutesFailure() {
        val validator = mock<InitialSetupYearWeightingModelValidator> {
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
            on { addItemToDatabase<AppConfiguration>(
                    eq("id"),
                    any(),
                    eq(onError),
                    eq(onSuccess))} doAnswer { onSuccess() }
        }

        initialSetupYearWeightingViewModelTester(validator, storageProvider, authProvider)
                .withInitialSetupDataModel(courseLength = 1)
                .performActions {
                    bindModelForCurrentYear { it.value = 50 }
                    finishEditingCurrentYear()
                    saveInitialSetup(onError, onSuccess)
                }

        verify(onError).invoke(any())
        verifyZeroInteractions(onSuccess)
    }
}