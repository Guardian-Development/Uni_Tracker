package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator

class ConfigurationYearWeightingViewModel(
        private val dataValidator: ConfigurationYearWeightingModelValidator,
        private val dataStorage: IProvideDataStorage,
        private val authProvider: IProvideAuthentication) : ViewModel()
{
    var configurationData: ConfigurationDataModel? = null

    var currentYear = 1
        private set

    val completedWeightingsForAllYear: Boolean
        get() = configurationData?.courseLength == enteredYearWeightings.size

    val validDataEnteredForCurrentYearWeighting: Boolean
        get() = dataValidator.validate(currentYearWeighting.value)

    private val enteredYearWeightings: MutableList<ConfigurationYearWeightingModel> = mutableListOf()
    private var currentYearWeighting: MutableLiveData<Int> = MutableLiveData()

    fun bindModelForCurrentYear(bind: (MutableLiveData<Int>) -> Unit)
    {
        when(configurationData) {
            null -> throw IllegalStateException("cannot get model without setting configurationData")
            else -> {
                bind(currentYearWeighting)
            }
        }
    }

    fun finishEditingCurrentYear()
    {
        if(!validDataEnteredForCurrentYearWeighting) {
            throw IllegalStateException("Invalid data entered for the current year weighting")
        }

        enteredYearWeightings.add(
                ConfigurationYearWeightingModel(currentYear, currentYearWeighting.value!!))

        if (completedWeightingsForAllYear) {
            return
        }

        currentYear += 1
        currentYearWeighting = MutableLiveData()
    }

    fun saveConfiguration(onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        val appConfiguration = buildAppConfiguration()

        when(authProvider.userLoggedIn) {
            true -> dataStorage.addItemToDatabase(
                    authProvider.userUniqueId!! + "/configuration",
                    appConfiguration,
                    onError,
                    onSuccess)
            false -> onError("User not logged in")
        }
    }

    private fun buildAppConfiguration() : Configuration
    {
        val yearWeightings = enteredYearWeightings.map {
            ConfigurationYearWeighting(it.year, it.weighting)
        }

        return Configuration(
                configurationData!!.universityName,
                configurationData!!.yearStarted,
                configurationData!!.courseLength,
                configurationData!!.targetPercentage,
                configurationData!!.totalCredits,
                yearWeightings)
    }
}