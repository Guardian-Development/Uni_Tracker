package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

/**
 * The view model is responsible for presenting information to the modules view.
 *
 * @param userState provides functionality to query over the users configuration.
 */
class ModulesViewModel(private val userState: IQueryUserState) : ViewModel()
{
    val currentModules: MutableLiveData<List<ModuleModel>> = MutableLiveData()

    /**
     * Responsible for refreshing the current set of modules the user has configured.
     * Converts the domain modules to the modules model in order to be displayed.
     */
    fun refreshCurrentModules()
    {
        userState.getUserModules({ Log.e("AddModuleViewModel", it)}) {
            currentModules.postValue(
                    it.map { ModuleModel(
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
}