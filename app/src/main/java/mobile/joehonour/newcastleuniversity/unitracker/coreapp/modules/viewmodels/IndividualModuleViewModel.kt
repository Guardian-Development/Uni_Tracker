package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult

class IndividualModuleViewModel(private val moduleCalculator: IProvideModuleCalculations) : ViewModel()
{
    val module: MutableLiveData<ModuleModel> = MutableLiveData()
    val percentageComplete: MutableLiveData<Double> = MutableLiveData()
    val currentGrade: MutableLiveData<Double> = MutableLiveData()

    fun calculatePercentageComplete(onError: (String?) -> Unit, onSuccess: (Double) -> Unit) =
            performCalculationOnModule(
                    onError,
                    onSuccess,
                    moduleCalculator::calculatePercentageCompleteOf)

    fun calculateCurrentGrade(onError: (String?) -> Unit, onSuccess: (Double) -> Unit) =
        performCalculationOnModule(
                onError,
                onSuccess,
                moduleCalculator::calculateCurrentAverageGradeOf)

    private fun performCalculationOnModule(onError: (String?) -> Unit,
                                           onSuccess: (Double) -> Unit,
                                           calculation: (Module) -> Double)
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
                moduleModel.moduleCode,
                moduleModel.moduleName,
                moduleModel.moduleCredits,
                moduleModel.moduleYearStudied,
                mapOfResults)
    }
}