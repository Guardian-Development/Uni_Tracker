package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelValidator

/**
 * The view model is responsible for presenting information to the configuration view.
 *
 * @param dataValidator provides functionality to validate a users configuration.
 */
class ConfigurationViewModel(private val dataValidator: ConfigurationDataModelValidator) : ViewModel()
{
    val universityName: MutableLiveData<String> = MutableLiveData()
    val yearStarted: MutableLiveData<Int> = MutableLiveData()
    val courseLength: MutableLiveData<Int> = MutableLiveData()
    val targetPercentage: MutableLiveData<Int> = MutableLiveData()
    val totalCredits: MutableLiveData<Int> = MutableLiveData()

    /**
     * Responsible for validating the users entry by evaluating the mutable data fields within this
     * view model against the data validator.
     */
    val validDataEntered: Boolean
        get() = dataValidator.validate(
                universityName.value,
                yearStarted.value,
                courseLength.value,
                targetPercentage.value,
                totalCredits.value
        )

    /**
     * Responsible for building the users configuration from the mutable data fields.
     */
    fun buildConfigurationData() : ConfigurationDataModel
    {
        if(!validDataEntered)
        {
            throw IllegalStateException("You cannot complete configuration with the values entered")
        }

        return ConfigurationDataModel(
                universityName.value!!,
                yearStarted.value!!,
                courseLength.value!!,
                targetPercentage.value!!,
                totalCredits.value!!)
    }
}

