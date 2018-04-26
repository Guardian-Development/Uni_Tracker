package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.AddResultModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys
import java.util.*

class AddResultViewModel(private val userState: IQueryUserState,
                         private val dataStorage: IProvideDataStorage,
                         private val authProvider: IProvideAuthentication,
                         private val addResultModelValidator: AddResultModelValidator) : ViewModel()
{
    val availableModules: MutableLiveData<List<ModuleModel>> = MutableLiveData()

    val addResultModule: MutableLiveData<ModuleModel> = MutableLiveData()
    val addResultName: MutableLiveData<String> = MutableLiveData()
    val addResultWeightingPercentage: MutableLiveData<Int> = MutableLiveData()
    val addResultPercentage: MutableLiveData<Double> = MutableLiveData()

    val validDataEntered : Boolean
        get() = addResultModelValidator.validate(
                addResultName.value,
                addResultWeightingPercentage.value,
                addResultPercentage.value)

    fun refreshAvailableModules()
    {
        userState.getUserModules({ Log.e("AddModuleViewModel", it)}) {
            availableModules.postValue(
                    it.map { ModuleModel(
                            it.moduleCode,
                            it.moduleName,
                            it.moduleCredits,
                            it.moduleYearStudied) })
        }
    }

    fun saveResultForModule(resultId: String, onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        if(validDataEntered)
        {
            val result = buildResultForModule(resultId)
            when(authProvider.userLoggedIn)
            {
                true -> {
                    dataStorage.addItemToDatabase(
                            DataLocationKeys.resultLocationForModule(
                                    authProvider.userUniqueId!!,
                                    addResultModule.value!!.moduleCode,
                                    result.resultId),
                            result,
                            onError,
                            onSuccess)
                }
                false -> onError("User not logged in")
            }
        }
        else
        {
            onError("Invalid data entered")
        }
    }

    private fun buildResultForModule(resultId: String) : ModuleResult =
            ModuleResult(resultId,
                    addResultName.value!!,
                    addResultWeightingPercentage.value!!,
                    addResultPercentage.value!!)
}