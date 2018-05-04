package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.AddResultModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.viewmodels.AddResultViewModelTester.Companion.addResultViewModelTester
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AddResultViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canGetValidDataEnteredValid()
    {
        val validator = mock<AddResultModelValidator> {
            on { validate("result name", 50, 90.0) } doReturn true
        }

        addResultViewModelTester(validator = validator)
                .performActions {
                    addResultModule.value = ModuleModel("id", "CSC3128", "test", 10, 2)
                    addResultName.value = "result name"
                    addResultWeightingPercentage.value = 50
                    addResultPercentage.value = 90.0
                }
                .assertValidDataTrue()
    }

    @Test
    fun canGetValidDataEnteredInvalid()
    {
        val validator = mock<AddResultModelValidator> {
            on { validate("result name", 50, 90.0) } doReturn false
        }

        addResultViewModelTester(validator = validator)
                .performActions {
                    addResultModule.value = ModuleModel("id", "CSC3128", "test", 10, 2)
                    addResultName.value = "result name"
                    addResultWeightingPercentage.value = 50
                    addResultPercentage.value = 90.0
                }
                .assertValidDataFalse()
    }

    @Test
    fun refreshCurrentModulesSuccessSetsPublicData()
    {
        val modules = listOf(
                Module("id", "CSC3123", "module", 10, 2, emptyMap()),
                Module("id2", "CSC9876", "module2", 20, 1, emptyMap()))

        val userStateQuery = mock<IQueryUserState> {
            on { getUserModules(any(), any()) } doAnswer {
                val onSuccess = it.arguments[1] as ((List<Module>) -> Unit)
                onSuccess(modules)
            }
        }

        addResultViewModelTester(userState = userStateQuery)
                .performActions {
                    refreshAvailableModules()
                }
                .assertAvailableModulesSet(modules)
    }

    @Test
    fun refreshCurrentModulesFailureDoesNotSetPublicData()
    {
        val userStateQuery = mock<IQueryUserState> {
            on { getUserModules(any(), any()) } doAnswer {
                val onError = it.arguments[0] as ((String?) -> Unit)
                onError("database error")
            }
        }

        addResultViewModelTester(userState = userStateQuery)
                .performActions {
                    refreshAvailableModules()
                }
                .assertAvailableModulesNull()
    }

    @Test
    fun saveResultValidDataEnteredUserLoggedInSuccess()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<AddResultModelValidator> {
            on { validate("add result", 50, 82.1) } doReturn true
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "testuser"
            on { userLoggedIn } doReturn true
        }

        val dataStore = mock<IProvideDataStorage> {
            on { addItemToDatabase<ModuleResult>(
                    eq("testuser/modules/id/results/resultid"), any(), eq(onError), eq(onSuccess)) } doAnswer {
                onSuccess()
            }
        }

        addResultViewModelTester(validator = validator, authProvider = authProvider, dataStorage = dataStore)
                .performActions {
                    addResultModule.value = ModuleModel("id", "CSC123", "test", 50, 2)
                    addResultName.value = "add result"
                    addResultWeightingPercentage.value = 50
                    addResultPercentage.value = 82.1
                }
                .performActions {
                    saveResultForModule("resultid", onError, onSuccess)
                }

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun saveResultValidDataEnteredUserNotLoggedInFail()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<AddResultModelValidator> {
            on { validate("add result", 50, 82.1) } doReturn true
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        addResultViewModelTester(validator = validator, authProvider = authProvider)
                .performActions {
                    addResultModule.value = ModuleModel("id", "CSC123", "test", 50, 2)
                    addResultName.value = "add result"
                    addResultWeightingPercentage.value = 50
                    addResultPercentage.value = 82.1
                }
                .performActions {
                    saveResultForModule("test", onError, onSuccess)
                }

        verify(onError).invoke("User not logged in")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun saveResultInValidDataEnteredUserLoggedInFail()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<AddResultModelValidator> {
            on { validate("add result", 50, 82.1) } doReturn false
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "testuser"
            on { userLoggedIn } doReturn true
        }

        addResultViewModelTester(validator = validator, authProvider = authProvider)
                .performActions {
                    addResultModule.value = ModuleModel("id", "CSC123", "test", 50, 2)
                    addResultName.value = "add result"
                    addResultWeightingPercentage.value = 50
                    addResultPercentage.value = 82.1
                }
                .performActions {
                    saveResultForModule("test", onError, onSuccess)
                }

        verify(onError).invoke("Invalid data entered")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun saveResultInValidDataEnteredUserNotLoggedInFail()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<AddResultModelValidator> {
            on { validate("add result", 50, 82.1) } doReturn false
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        addResultViewModelTester(validator = validator, authProvider = authProvider)
                .performActions {
                    addResultModule.value = ModuleModel("id", "CSC123", "test", 50, 2)
                    addResultName.value = "add result"
                    addResultWeightingPercentage.value = 50
                    addResultPercentage.value = 82.1
                }
                .performActions {
                    saveResultForModule("test", onError, onSuccess)
                }

        verify(onError).invoke("Invalid data entered")
        verifyZeroInteractions(onSuccess)
    }
}