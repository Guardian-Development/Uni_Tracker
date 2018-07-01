package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class IndividualModuleViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun calculatePercentageCompleteModuleValueNotNullSuccess()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<((Double) -> Unit)> {
            on { invoke(any()) } doAnswer { FieldAssert(10.0).doAssert(it.arguments[0] as Double) }
        }
        val calculator = mock<IProvideModuleCalculations> {
            on { calculatePercentageCompleteOf(any()) } doReturn 10.0
        }
        val viewModel = IndividualModuleViewModel(calculator, mock(), mock())
        viewModel.module.value = ModuleModel("uniqueId", "CSCTEST", "test", 10, 2, listOf(
                ModuleResultModel("id", "name", 40, 20.0)))
        viewModel.calculatePercentageComplete(onError, onSuccess)

        verify(onSuccess).invoke(10.0)
        verifyZeroInteractions(onError)
    }

    @Test
    fun calculatePercentageCompleteModuleValueNotNullErrorThrown()
    {
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("exception message").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<((Double) -> Unit)>()

        val calculator = mock<IProvideModuleCalculations> {
            on { calculatePercentageCompleteOf(any()) } doAnswer {
                throw IllegalStateException("exception message")
            }
        }

        val viewModel = IndividualModuleViewModel(calculator, mock(), mock())
        viewModel.module.value = ModuleModel("uniqueId", "CSCTEST", "test", 10, 2, listOf(
                ModuleResultModel("id", "name", 40, 20.0)))
        viewModel.calculatePercentageComplete(onError, onSuccess)

        verify(onError).invoke("exception message")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculatePercentageCompleteModuleValueNullFailure()
    {
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Module was not set").doAssert(it.arguments[0] as String)
            }
        }

        val onSuccess = mock<((Double) -> Unit)>()

        val viewModel = IndividualModuleViewModel(mock(), mock(), mock())
        viewModel.calculatePercentageComplete(onError, onSuccess)

        verify(onError).invoke("Module was not set")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculateCurrentGradeModuleValueNotNullSuccess()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<((Int) -> Unit)> {
            on { invoke(any()) } doAnswer { FieldAssert(30).doAssert(it.arguments[0] as Int) }
        }
        val calculator = mock<IProvideModuleCalculations> {
            on { calculateCurrentAverageGradeOf(any()) } doReturn 30
        }
        val viewModel = IndividualModuleViewModel(calculator, mock(), mock())
        viewModel.module.value = ModuleModel("uniqueId", "CSCTEST", "test", 10, 2, listOf(
                ModuleResultModel("id", "name", 40, 20.0)))
        viewModel.calculateCurrentGrade(onError, onSuccess)

        verify(onSuccess).invoke(30)
        verifyZeroInteractions(onError)
    }

    @Test
    fun calculateCurrentGradeModuleValueNotNullErrorThrown()
    {
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("exception message").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<((Int) -> Unit)>()

        val calculator = mock<IProvideModuleCalculations> {
            on { calculateCurrentAverageGradeOf(any()) } doAnswer {
                throw IllegalStateException("exception message")
            }
        }

        val viewModel = IndividualModuleViewModel(calculator, mock(), mock())
        viewModel.module.value = ModuleModel("uniqueId", "CSCTEST", "test", 10, 2, listOf(
                ModuleResultModel("id", "name", 40, 20.0)))
        viewModel.calculateCurrentGrade(onError, onSuccess)

        verify(onError).invoke("exception message")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculateCurrentGradeModuleValueNullFailure() {
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Module was not set").doAssert(it.arguments[0] as String)
            }
        }

        val onSuccess = mock<((Int) -> Unit)>()

        val viewModel = IndividualModuleViewModel(mock(), mock(), mock())
        viewModel.calculateCurrentGrade(onError, onSuccess)

        verify(onError).invoke("Module was not set")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun canDeleteResultForModuleSuccess()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "testUser"
        }
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()
        val dataAccess = mock<IProvideDataStorage> {
            on { deleteItemFromDatabase(
                    eq("testUser/modules/testModule/results/testResult"),
                    eq(onError),
                    eq(onSuccess)) } doAnswer { (it.arguments[2] as (() -> Unit)).invoke() }
        }
        val module = mock<ModuleModel> {
            on { moduleId } doReturn  "testModule"
        }

        val viewModel = IndividualModuleViewModel(mock(), dataAccess, authProvider)
        viewModel.module.value = module

        viewModel.deleteResultForModule("testResult", onError, onSuccess)

        verify(onSuccess).invoke()
        verifyZeroInteractions(onError)
    }

    @Test
    fun canDeleteResultForModuleUserNotLoggedInFails()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()

        val viewModel = IndividualModuleViewModel(mock(), mock(), authProvider)
        viewModel.module.value = mock()

        viewModel.deleteResultForModule("testResult", onError, onSuccess)

        verify(onError).invoke("You must be signed in to delete a result.")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun canDeleteResultForModuleDatabaseErrorFails()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "testUser"
        }
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()
        val dataAccess = mock<IProvideDataStorage> {
            on { deleteItemFromDatabase(
                    eq("testUser/modules/testModule/results/testResult"),
                    eq(onError),
                    eq(onSuccess)) } doAnswer { (it.arguments[1] as ((String?) -> Unit)).invoke("Database error") }
        }
        val module = mock<ModuleModel> {
            on { moduleId } doReturn  "testModule"
        }

        val viewModel = IndividualModuleViewModel(mock(), dataAccess, authProvider)
        viewModel.module.value = module

        viewModel.deleteResultForModule("testResult", onError, onSuccess)

        verify(onError).invoke("Database error")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun canDeleteResultForModuleModuleNotSetFails()
    {
        val onError = mock<((String?) -> Unit)>()
        val onSuccess = mock<(() -> Unit)>()
        val viewModel = IndividualModuleViewModel(mock(), mock(), mock())

        viewModel.deleteResultForModule("testResult", onError, onSuccess)

        verify(onError).invoke("Module was not set")
        verifyZeroInteractions(onSuccess)
    }
}