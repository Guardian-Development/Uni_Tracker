package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.AppConfiguration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.AppConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModel
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModelValidator

class InitialSetupYearWeightingViewModel(
        private val dataValidator: InitialSetupYearWeightingModelValidator,
        private val dataStorage: IProvideDataStorage,
        private val authProvider: IProvideAuthentication) : ViewModel()
{
    var initialSetupData: InitialSetupDataModel? = null

    var currentYear = 1
        private set

    val completedWeightingsForAllYear: Boolean
        get() = initialSetupData?.courseLength == enteredYearWeightings.size

    val validDataEnteredForCurrentYearWeighting: Boolean
        get() = dataValidator.validate(currentYearWeighting.value)

    private val enteredYearWeightings: MutableList<InitialSetupYearWeightingModel> = mutableListOf()
    private var currentYearWeighting: MutableLiveData<Int> = MutableLiveData()

    fun bindModelForCurrentYear(bind: (MutableLiveData<Int>) -> Unit)
    {
        when(initialSetupData) {
            null -> throw IllegalStateException("cannot get model without setting initialSetupData")
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
                InitialSetupYearWeightingModel(currentYear, currentYearWeighting.value!!))

        if (completedWeightingsForAllYear) {
            return
        }

        currentYear += 1
        currentYearWeighting = MutableLiveData()
    }

    fun saveInitialSetup(onError: (String?) -> Unit, onSuccess: () -> Unit)
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

    private fun buildAppConfiguration() : AppConfiguration
    {
        val yearWeightings = enteredYearWeightings.map {
            AppConfigurationYearWeighting(it.year, it.weighting)
        }

        return AppConfiguration(
                initialSetupData!!.universityName,
                initialSetupData!!.yearStarted,
                initialSetupData!!.courseLength,
                initialSetupData!!.targetPercentage,
                initialSetupData!!.totalCredits,
                yearWeightings)
    }
}