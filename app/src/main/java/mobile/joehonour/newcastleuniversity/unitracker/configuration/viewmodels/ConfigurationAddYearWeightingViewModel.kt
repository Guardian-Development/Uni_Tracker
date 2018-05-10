package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator

class ConfigurationAddYearWeightingViewModel(
        private val dataValidator: ConfigurationYearWeightingModelValidator) : ViewModel()
{
    var yearNumber: MutableLiveData<Int> = MutableLiveData()
    var yearWeighting: MutableLiveData<Int> = MutableLiveData()
    var yearCreditsCompleted: MutableLiveData<Int> = MutableLiveData()

    val validDataEnteredForYear: Boolean
        get() = dataValidator.validate(
                yearNumber.value,
                yearWeighting.value,
                yearCreditsCompleted.value)

    fun buildYearWeightingModel() : ConfigurationYearWeightingModel
    {
        return when(validDataEnteredForYear) {
            true -> ConfigurationYearWeightingModel(
                    yearNumber.value!!,
                    yearWeighting.value!!,
                    yearCreditsCompleted.value!!)
            false -> throw IllegalStateException("Invalid data entered for the year weighting")
        }
    }
}