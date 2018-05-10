package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

class ConfigurationYearWeightingViewModel(
        private val dataStorage: IProvideDataStorage,
        private val authProvider: IProvideAuthentication) : ViewModel()
{
    var configurationData: ConfigurationDataModel? = null
    val yearWeightings: MutableLiveData<MutableList<ConfigurationYearWeightingModel>> = MutableLiveData()

    init {
        yearWeightings.postValue(mutableListOf())
    }

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

    private fun buildAppConfiguration() : Configuration
    {
        val domainYearWeightings = yearWeightings.value!!
                .map { ConfigurationYearWeighting(it.year, it.weighting, it.creditsCompletedWithinYear) }

        val totalCredits = domainYearWeightings.map { it.creditsCompletedWithinYear }.sum()

        return Configuration(
                configurationData!!.universityName,
                configurationData!!.yearStarted,
                domainYearWeightings.size,
                configurationData!!.targetPercentage,
                totalCredits,
                domainYearWeightings)
    }
}