package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
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
        val viewModel = IndividualModuleViewModel(calculator)
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

        val viewModel = IndividualModuleViewModel(calculator)
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

        val viewModel = IndividualModuleViewModel(mock())
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
        val viewModel = IndividualModuleViewModel(calculator)
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

        val viewModel = IndividualModuleViewModel(calculator)
        viewModel.module.value = ModuleModel("uniqueId", "CSCTEST", "test", 10, 2, listOf(
                ModuleResultModel("id", "name", 40, 20.0)))
        viewModel.calculateCurrentGrade(onError, onSuccess)

        verify(onError).invoke("exception message")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculateCurrentGradeModuleValueNullFailure()
    {
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Module was not set").doAssert(it.arguments[0] as String)
            }
        }

        val onSuccess = mock<((Int) -> Unit)>()

        val viewModel = IndividualModuleViewModel(mock())
        viewModel.calculateCurrentGrade(onError, onSuccess)

        verify(onError).invoke("Module was not set")
        verifyZeroInteractions(onSuccess)
    }
}