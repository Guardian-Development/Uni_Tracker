package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult

/**
 * The view model is responsible for presenting information to the individual module view.
 *
 * @param moduleCalculator provides functionality to perform calculations against an individual module.
 */
class IndividualModuleViewModel(private val moduleCalculator: IProvideModuleCalculations) : ViewModel()
{
    val module: MutableLiveData<ModuleModel> = MutableLiveData()
    val percentageComplete: MutableLiveData<Double> = MutableLiveData()
    val currentGrade: MutableLiveData<Int> = MutableLiveData()

    /**
     * Responsible for calculating the percentage complete of the current module.
     *
     * @param onError is executed if the calculation fails for any reason.
     * @param onSuccess is executed when the calculation succeeds, being passed the result.
     */
    fun calculatePercentageComplete(onError: (String?) -> Unit, onSuccess: (Double) -> Unit) =
            performCalculationOnModule(
                    onError,
                    onSuccess,
                    moduleCalculator::calculatePercentageCompleteOf)

    /**
     * Responsible for calculating the current grade of a module, based on the results entered.
     *
     * @param onError is executed if the calculation fails for any reason.
     * @param onSuccess is executed when the calculation succeeds, being passed the result.
     */
    fun calculateCurrentGrade(onError: (String?) -> Unit, onSuccess: (Int) -> Unit) =
        performCalculationOnModule(
                onError,
                onSuccess,
                moduleCalculator::calculateCurrentAverageGradeOf)

    /**
     * Responsible for performing a calculation against an individual module.
     *
     * @param onError is executed if an error occurs when attempting to perform the calculation.
     * @param onSuccess is executed on successful completion of the calculation, being passed the result.
     * @param calculation is the calculation you wish to perform on the individual module.
     */
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

    /**
     * Responsible for converting the module model to the domain module representation,
     * which then allows calculations to be performed against it.
     *
     * @param moduleModel the model representation of the module.
     *
     * @return the domain specific module.
     */
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