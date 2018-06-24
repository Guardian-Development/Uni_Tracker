package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelValidator

class ConfigurationViewModel(private val dataValidator: ConfigurationDataModelValidator) : ViewModel()
{
    val universityName: MutableLiveData<String> = MutableLiveData()
    val yearStarted: MutableLiveData<Int> = MutableLiveData()
    val targetPercentage: MutableLiveData<Int> = MutableLiveData()

    val validDataEntered: Boolean
        get() = dataValidator.validate(
                universityName.value,
                yearStarted.value,
                targetPercentage.value
        )

    fun buildConfigurationData() : ConfigurationDataModel
    {
        if(!validDataEntered)
        {
            throw IllegalStateException("You cannot complete configuration with the values entered")
        }

        return ConfigurationDataModel(
                universityName.value!!,
                yearStarted.value!!,
                targetPercentage.value!!)
    }
}

