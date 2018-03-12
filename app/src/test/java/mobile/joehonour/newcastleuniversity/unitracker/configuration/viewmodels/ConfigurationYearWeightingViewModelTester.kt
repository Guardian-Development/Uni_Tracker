package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator

class ConfigurationYearWeightingViewModelTester(private val viewModel: ConfigurationYearWeightingViewModel)
{
    fun withConfigurationDataModel(
            universityName: String = "default",
            yearStarted: Int = 0,
            courseLength: Int = 0,
            targetPercentage: Int = 0,
            totalCredits: Int = 0) : ConfigurationYearWeightingViewModelTester
    {
        val configurationData = ConfigurationDataModel(
                universityName,
                yearStarted,
                courseLength,
                targetPercentage,
                totalCredits)

        viewModel.configurationData = configurationData
        return this
    }

    fun performActions(action: ConfigurationYearWeightingViewModel.() -> Unit)
            : ConfigurationYearWeightingViewModelTester
    {
        action.invoke(viewModel)
        return this
    }

    fun assertCompletedWeightingsForAllYearsFalse() : ConfigurationYearWeightingViewModelTester
    {
        assertFalse(viewModel.completedWeightingsForAllYear)
        return this
    }

    fun assertCompletedWeightingsForAllYearsTrue()
            = assertTrue(viewModel.completedWeightingsForAllYear)

    fun assertValidDataEnteredForCurrentYearWeightingTrue()
            = assertTrue(viewModel.validDataEnteredForCurrentYearWeighting)

    fun assertValidDataEnteredForCurrentYearWeightingFalse()
            = assertFalse(viewModel.validDataEnteredForCurrentYearWeighting)

    companion object
    {
        fun configurationYearWeightingViewModelTester(
                validator: ConfigurationYearWeightingModelValidator = mock(),
                dataStorage: IProvideDataStorage = mock(),
                authProvider: IProvideAuthentication = mock())
                : ConfigurationYearWeightingViewModelTester
        {
            return ConfigurationYearWeightingViewModelTester(
                    ConfigurationYearWeightingViewModel(validator, dataStorage, authProvider))
        }
    }
}