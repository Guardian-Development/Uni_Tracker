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

/**
 * The view model is responsible for responsible for presenting information to the add result view.
 *
 * @param userState provides functionality to query over the users configuration.
 * @param dataStorage provides functionality to save information to the database.
 * @param authProvider provides functionality to deem if the users authentication status.
 * @param addResultModelValidator provides functionality to validate a result.
 */
class AddResultViewModel(private val userState: IQueryUserState,
                         private val dataStorage: IProvideDataStorage,
                         private val authProvider: IProvideAuthentication,
                         private val addResultModelValidator: AddResultModelValidator) : ViewModel()
{
    /**
     * Responsible for providing the list of available modules a user can enter results against.
     */
    val availableModules: MutableLiveData<List<ModuleModel>> = MutableLiveData()

    val addResultModule: MutableLiveData<ModuleModel> = MutableLiveData()
    val addResultName: MutableLiveData<String> = MutableLiveData()
    val addResultWeightingPercentage: MutableLiveData<Int> = MutableLiveData()
    val addResultPercentage: MutableLiveData<Double> = MutableLiveData()

    /**
     * Responsible for displaying whether the users current information entered is valid, using the
     * view models fields.
     */
    val validDataEntered : Boolean
        get() = addResultModelValidator.validate(
                addResultName.value,
                addResultWeightingPercentage.value,
                addResultPercentage.value)

    /**
     * Responsible for refreshing the list of available modules a user can add a result against.
     */
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

    /**
     * Responsible for saving an entered result to the database with the given resultId.
     * If a user has not entered valid data, or is not logged in, then onError is immediately called.
     *
     * @param resultId is the id you want to be associated with this result.
     * @param onError is executed if an error occurs during the saving of the result.
     * @param onSuccess is executed when the result is successfully stored to the database.
     */
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

    /**
     * Responsible for building the result domain model from the entered fields.
     */
    private fun buildResultForModule(resultId: String) : ModuleResult =
            ModuleResult(resultId,
                    addResultName.value!!,
                    addResultWeightingPercentage.value!!,
                    addResultPercentage.value!!)
}