package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import kotlin.math.roundToInt

class ModuleCalculator : IProvideModuleCalculations
{
    override fun calculatePercentageCompleteOf(module: Module): Double
    {
        //sums all weightings recorded to get completion percentage.
        val resultsPercentage = module.results.values.map { it.resultWeighting }.sum()
        return when
        {
            resultsPercentage <= 100 -> resultsPercentage.toDouble()
            else -> throw IllegalStateException(
                    "Cannot have results totalling more than 100% for a module")
        }
    }

    override fun calculateCurrentAverageGradeOf(module: Module): Int
    {
        return when {
            module.results.isEmpty() -> 0
            else -> weightedAverageOfResultsWithinModule(module.results.values.toList())
                    .roundToInt()
        }
    }
}