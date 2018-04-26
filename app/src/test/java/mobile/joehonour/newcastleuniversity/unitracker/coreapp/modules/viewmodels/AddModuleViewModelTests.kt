package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.AddModuleViewModelTester.Companion.addModuleViewModelTester
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AddModuleViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canGetValidDataEnteredValid()
    {
        val validator = mock<ModuleModelValidator> {
            on { validate("CSC3123", "Programming", 10, 1) } doReturn true
        }

        addModuleViewModelTester(validator = validator)
                .performActions {
                    moduleCode.value = "CSC3123"
                    moduleName.value = "Programming"
                    moduleCredits.value = 10
                    moduleYearStudied.value = 1
                }
                .assertValidDataTrue()
    }

    @Test
    fun canGetValidDataEnteredInvalid()
    {
        val validator = mock<ModuleModelValidator> {
            on { validate("CSC15", "Software", 20, 2) } doReturn false
        }

        addModuleViewModelTester(validator = validator)
                .performActions {
                    moduleCode.value = "CSC15"
                    moduleName.value = "Software"
                    moduleCredits.value = 20
                    moduleYearStudied.value = 2
                }
                .assertValidDataFalse()
    }

    @Test
    fun availableYearsStudiedReturnedCorrectly()
    {
        val onError = mock<((String?) -> Unit)>()
        val configuration = Configuration("name", 2016, 2, 70, 260, listOf(
                ConfigurationYearWeighting(1, 50, 120),
                ConfigurationYearWeighting(2, 50, 120)))

        val userState = mock<IQueryUserState> {
            on { getUserConfiguration(eq(onError), any()) } doAnswer {
                val onSuccess = it.arguments[1] as ((Configuration) -> Unit)
                onSuccess(configuration)
            }
        }

        val assert = UnorderedListAssert<Int, Int>({ it })
        assert.asserts.put(1, FieldAssert(1))
        assert.asserts.put(2, FieldAssert(2))
        val onSuccessList = { list:List<Int> -> assert.doAssert(list)}

        addModuleViewModelTester(userState = userState)
                .performActions {
                    availableYearsStudied(onError, onSuccessList)
                }

        verifyZeroInteractions(onError)
    }

    @Test
    fun saveModuleValidDataEnteredUserLoggedInSuccess()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<ModuleModelValidator> {
            on { validate("CSC123", "Testing", 10, 3) } doReturn true
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "testuser"
            on { userLoggedIn } doReturn true
        }

        val dataStore = mock<IProvideDataStorage> {
            on { addItemToDatabase<Module>(
                    eq("testuser/modules/CSC123/"), any(), eq(onError), eq(onSuccess)) } doAnswer { onSuccess() }
        }

        addModuleViewModelTester(validator = validator, authProvider = authProvider, dataStorage = dataStore)
                .performActions {
                    moduleCode.value = "CSC123"
                    moduleName.value = "Testing"
                    moduleCredits.value = 10
                    moduleYearStudied.value = 3
                }
                .performActions {
                    saveModule(onError, onSuccess)
                }

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun saveModuleValidDataEnteredUserNotLoggedInFail()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<ModuleModelValidator> {
            on { validate("CSC123", "Testing", 10, 3) } doReturn true
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        addModuleViewModelTester(validator = validator, authProvider = authProvider)
                .performActions {
                    moduleCode.value = "CSC123"
                    moduleName.value = "Testing"
                    moduleCredits.value = 10
                    moduleYearStudied.value = 3
                }
                .performActions {
                    saveModule(onError, onSuccess)
                }

        verify(onError).invoke("User not logged in")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun saveModuleInValidDataEnteredUserLoggedInFail()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<ModuleModelValidator> {
            on { validate("CSC123", "Testing", 10, 3) } doReturn false
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "testuser"
            on { userLoggedIn } doReturn true
        }

        addModuleViewModelTester(validator = validator, authProvider = authProvider)
                .performActions {
                    moduleCode.value = "CSC123"
                    moduleName.value = "Testing"
                    moduleCredits.value = 10
                    moduleYearStudied.value = 3
                }
                .performActions {
                    saveModule(onError, onSuccess)
                }

        verify(onError).invoke("Invalid data entered")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun saveModuleInValidDataEnteredUserNotLoggedInFail()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val validator = mock<ModuleModelValidator> {
            on { validate("CSC123", "Testing", 10, 3) } doReturn false
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        addModuleViewModelTester(validator = validator, authProvider = authProvider)
                .performActions {
                    moduleCode.value = "CSC123"
                    moduleName.value = "Testing"
                    moduleCredits.value = 10
                    moduleYearStudied.value = 3
                }
                .performActions {
                    saveModule(onError, onSuccess)
                }

        verify(onError).invoke("Invalid data entered")
        verifyZeroInteractions(onSuccess)
    }
}

