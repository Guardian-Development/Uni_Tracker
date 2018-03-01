package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModel
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModelValidator

class InitialSetupYearWeightingViewModelTester(private val viewModel: InitialSetupYearWeightingViewModel) {

    fun withInitialSetupDataModel(
            universityName: String = "default",
            yearStarted: Int = 0,
            courseLength: Int = 0,
            targetPercentage: Int = 0,
            totalCredits: Int = 0) : InitialSetupYearWeightingViewModelTester {
        val initialSetupData = InitialSetupDataModel(
                universityName,
                yearStarted,
                courseLength,
                targetPercentage,
                totalCredits)

        viewModel.initialSetupData = initialSetupData
        return this
    }

    fun performActions(action: InitialSetupYearWeightingViewModel.() -> Unit) : InitialSetupYearWeightingViewModelTester {
        action.invoke(viewModel)
        return this
    }

    fun assertCompletedWeightingsForAllYearsFalse() : InitialSetupYearWeightingViewModelTester {
        assertFalse(viewModel.completedWeightingsForAllYear)
        return this
    }

    fun assertCompletedWeightingsForAllYearsTrue()
            = assertTrue(viewModel.completedWeightingsForAllYear)

    fun assertValidDataEnteredForCurrentYearWeightingTrue()
            = assertTrue(viewModel.validDataEnteredForCurrentYearWeighting)

    fun assertValidDataEnteredForCurrentYearWeightingFalse()
            = assertFalse(viewModel.validDataEnteredForCurrentYearWeighting)

    companion object {
        fun initialSetupYearWeightingViewModelTester(
                validator: InitialSetupYearWeightingModelValidator = mock(),
                dataStorage: IProvideDataStorage = mock(),
                authProvider: IProvideAuthentication = mock())
                : InitialSetupYearWeightingViewModelTester {
            return InitialSetupYearWeightingViewModelTester(
                    InitialSetupYearWeightingViewModel(validator, dataStorage, authProvider))
        }
    }
}