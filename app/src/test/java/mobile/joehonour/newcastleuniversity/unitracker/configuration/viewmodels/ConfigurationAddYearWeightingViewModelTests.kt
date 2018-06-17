package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TestRule

class ConfigurationAddYearWeightingViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()

    @Test
    fun canBuildYearWeightingSuccess()
    {
        val dataValidator = mock< ConfigurationYearWeightingModelValidator>{
            on { validate(1, 10, 50) } doReturn true
        }
        val viewModel = ConfigurationAddYearWeightingViewModel(dataValidator)
        viewModel.yearNumber.value = 1
        viewModel.yearWeighting.value = 10
        viewModel.yearCreditsCompleted.value = 50

        val result = viewModel.buildYearWeightingModel()
        ConfigurationYearWeightingModelAssert(
                FieldAssert(1),
                FieldAssert(10),
                FieldAssert(50)).doAssert(result)
    }

    @Test
    fun canBuildYearWeightingValidationFailsCausesFailure()
    {
        val dataValidator = mock< ConfigurationYearWeightingModelValidator>{
            on { validate(3, null, null) } doReturn false
        }
        val viewModel = ConfigurationAddYearWeightingViewModel(dataValidator)
        viewModel.yearNumber.value = 3

        thrown.expect(IllegalStateException::class.java)
        thrown.expectMessage("Invalid data entered for the year weighting")
        
        viewModel.buildYearWeightingModel()
    }
}

class ConfigurationYearWeightingModelAssert(
        private val year: FieldAssert<Int>,
        private val weighting: FieldAssert<Int>,
        private val creditsCompleted: FieldAssert<Int>) : Assert<ConfigurationYearWeightingModel>
{
    override fun doAssert(actualValue: ConfigurationYearWeightingModel)
    {
        year.doAssert(actualValue.year)
        weighting.doAssert(actualValue.weighting)
        creditsCompleted.doAssert(actualValue.creditsCompletedWithinYear)
    }
}