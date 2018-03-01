package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModel
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModelValidator

class InitialSetupViewModel(private val dataValidator: InitialSetupDataModelValidator) : ViewModel() {

    val universityName: MutableLiveData<String> = MutableLiveData()
    val yearStarted: MutableLiveData<Int> = MutableLiveData()
    val courseLength: MutableLiveData<Int> = MutableLiveData()
    val targetPercentage: MutableLiveData<Int> = MutableLiveData()
    val totalCredits: MutableLiveData<Int> = MutableLiveData()

    val validDataEntered: Boolean
        get() = dataValidator.validate(
                universityName.value,
                yearStarted.value,
                courseLength.value,
                targetPercentage.value,
                totalCredits.value
        )

    fun buildInitialSetupData() : InitialSetupDataModel {

        if(!validDataEntered)
        {
            throw IllegalStateException("You cannot complete initial setup with the values entered")
        }

        return InitialSetupDataModel(
                universityName.value!!,
                yearStarted.value!!,
                courseLength.value!!,
                targetPercentage.value!!,
                totalCredits.value!!)
    }
}

