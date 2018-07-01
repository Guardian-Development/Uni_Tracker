package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

class ModulesViewModel(private val userState: IQueryUserState,
                       private val dataStorage: IProvideDataStorage,
                       private val authProvider: IProvideAuthentication) : ViewModel()
{
    val currentModules: MutableLiveData<List<ModuleModel>> = MutableLiveData()

    fun refreshCurrentModules()
    {
        userState.getUserModules({ Log.e("AddModuleViewModel", it)}) {
            currentModules.postValue(
                    it.map { ModuleModel(
                            it.moduleId,
                            it.moduleCode,
                            it.moduleName,
                            it.moduleCredits,
                            it.moduleYearStudied,
                            it.results.values.map { ModuleResultModel(
                                    it.resultId,
                                    it.resultName,
                                    it.resultWeighting,
                                    it.resultPercentage) })
                    })
        }
    }

    fun deleteModule(moduleId: String, onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        when(authProvider.userLoggedIn) {
            true -> dataStorage.deleteItemFromDatabase(
                    DataLocationKeys.studentModuleLocation(authProvider.userUniqueId!!, moduleId),
                    onError,
                    onSuccess)
            false -> onError("You must be signed in to delete a result.")
        }
    }
}