package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

class IndividualModuleViewModel(private val moduleCalculator: IProvideModuleCalculations,
                                private val dataStorage: IProvideDataStorage,
                                private val authProvider: IProvideAuthentication) : ViewModel()
{
    val module: MutableLiveData<ModuleModel> = MutableLiveData()
    val percentageComplete: MutableLiveData<Double> = MutableLiveData()
    val currentGrade: MutableLiveData<Int> = MutableLiveData()

    fun calculatePercentageComplete(onError: (String?) -> Unit, onSuccess: (Double) -> Unit) =
            performCalculationOnModule(
                    onError,
                    onSuccess,
                    moduleCalculator::calculatePercentageCompleteOf)

    fun calculateCurrentGrade(onError: (String?) -> Unit, onSuccess: (Int) -> Unit) =
        performCalculationOnModule(
                onError,
                onSuccess,
                moduleCalculator::calculateCurrentAverageGradeOf)

    private fun <T> performCalculationOnModule(onError: (String?) -> Unit,
                                               onSuccess: (T) -> Unit,
                                               calculation: (Module) -> T)
    {
        when {
            module.value.notNull() -> {
                try {
                    val moduleToCalculate = convertModuleModelToModule(module.value!!)
                    onSuccess(calculation(moduleToCalculate))
                }
                catch (e: Exception) { onError(e.message) }
            }
            else -> onError("Module was not set")
        }
    }

    private fun convertModuleModelToModule(moduleModel: ModuleModel) : Module
    {
        val mapOfResults = moduleModel.results
                .map { it.resultId to ModuleResult(it.resultId, it.resultName, it.resultWeighting, it.resultPercentage) }
                .toMap()

        return Module(
                moduleModel.moduleId,
                moduleModel.moduleCode,
                moduleModel.moduleName,
                moduleModel.moduleCredits,
                moduleModel.moduleYearStudied,
                mapOfResults)
    }

    fun deleteResultForModule(resultId: String, onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        when(module.value.notNull()) {
            true ->
                when(authProvider.userLoggedIn) {
                    true -> dataStorage.deleteItemFromDatabase(
                            DataLocationKeys.resultLocationForModule(authProvider.userUniqueId!!,
                                    module.value!!.moduleId,
                                    resultId), onError, onSuccess)
                    false -> onError("You must be signed in to delete a result.")
            }
            false -> onError("Module was not set")
        }
    }
}