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
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

/**
 * The view model is responsible for presenting information to the year weighting view.
 *
 * @param dataValidator provides functionality to validate a year weighting model.
 * @param dataStorage provides functionality to save entities to the database.
 * @param authProvider provides functionality to query about the users authentication status.
 */
class ConfigurationYearWeightingViewModel(
        private val dataValidator: ConfigurationYearWeightingModelValidator,
        private val dataStorage: IProvideDataStorage,
        private val authProvider: IProvideAuthentication) : ViewModel()
{
    /**
     * Responsible for containing the configuration data already entered by the previous
     * configuration screen.
     */
    var configurationData: ConfigurationDataModel? = null

    /**
     * Responsible for displaying the current year that is being configured.
     */
    var currentYear = 1
        private set

    /**
     * Responsible for querying whether the user has entered data for all years within their degree.
     */
    val completedWeightingsForAllYear: Boolean
        get() = configurationData?.courseLength == enteredYearWeightings.size

    /**
     * Responsible for querying whether the values entered for the current year are valid.
     */
    val validDataEnteredForCurrentYearWeighting: Boolean
        get() = dataValidator.validate(currentYearWeighting.value, currentYearCreditsCompleted.value)

    /**
     * Responsible for holding a reference to all the years a user has successfully configured.
     */
    private val enteredYearWeightings: MutableList<ConfigurationYearWeightingModel> = mutableListOf()

    /**
     * Responsible for providing a binding point for the view for the year weighting currently
     * being entered.
     */
    var currentYearWeighting: MutableLiveData<Int> = MutableLiveData()

    /**
     * Responsible for providing a binding point for the view for the year credits currently being
     * entered.
     */
    var currentYearCreditsCompleted: MutableLiveData<Int> = MutableLiveData()

    /**
     * Responsible for letting the view bind to the fields within this view model, in order to save
     * the users input. The view must supply the previously entered configuration data for it to be
     * allowed to bind.
     */
    fun bindModelForCurrentYear(bind: ConfigurationYearWeightingViewModel.() -> Unit)
    {
        when(configurationData) {
            null -> throw IllegalStateException("cannot get model without setting configurationData")
            else -> {
                this.bind()
            }
        }
    }

    /**
     * Responsible for caching the users year weighting for the values they have currently entered.
     * Once these values are validated, this increments the current year being selected so the next
     * year can be configured if it exists.
     */
    fun finishEditingCurrentYear()
    {
        if(!validDataEnteredForCurrentYearWeighting) {
            throw IllegalStateException("Invalid data entered for the current year weighting")
        }

        enteredYearWeightings.add(
                ConfigurationYearWeightingModel(
                        currentYear,
                        currentYearWeighting.value!!,
                        currentYearCreditsCompleted.value!!))

        if (completedWeightingsForAllYear) {
            return
        }

        currentYear += 1
        currentYearWeighting = MutableLiveData()
    }

    /**
     * Responsible for saving the current configuration. Adds the users configuration to database,
     * before executing the error or success function respectively.
     *
     * @param onError is executed if something goes wrong when saving the users configuration
     * @param onSuccess is executed on successful saving of the users configuration.
     */
    fun saveConfiguration(onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        val appConfiguration = buildAppConfiguration()

        when(authProvider.userLoggedIn) {
            true -> dataStorage.addItemToDatabase(
                    DataLocationKeys.studentConfigurationLocation(authProvider.userUniqueId!!),
                    appConfiguration,
                    onError,
                    onSuccess)
            false -> onError("User not logged in")
        }
    }

    /**
     * Responsible for transforming the models used to store the users configuration to the domain
     * models that must be used to persist items to database.
     */
    private fun buildAppConfiguration() : Configuration
    {
        val yearWeightings = enteredYearWeightings.map {
            ConfigurationYearWeighting(it.year, it.weighting, it.creditsCompletedWithinYear)
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