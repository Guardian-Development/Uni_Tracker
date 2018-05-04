package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleResultModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

class ModulesViewModel(private val userState: IQueryUserState) : ViewModel()
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
}