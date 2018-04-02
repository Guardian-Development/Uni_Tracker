package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.roundTo2DecimalPlaces
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module

class ModuleCalculator : IProvideModuleCalculations
{
    override fun calculatePercentageCompleteOf(module: Module): Double
    {
        val resultsPercentage = module.results.values.map { it.resultWeighting }.sum()
        return when
        {
            resultsPercentage <= 100 -> resultsPercentage.toDouble()
            else -> throw IllegalStateException(
                    "Cannot have results totalling more than 100% for a module")
        }
    }

    override fun calculateCurrentAverageGradeOf(module: Module): Double
    {
        return when {
            module.results.isEmpty() -> 0.0.roundTo2DecimalPlaces()
            else -> module.results.values
                    .map { it.resultPercentage }
                    .average()
                    .roundTo2DecimalPlaces()
        }
    }
}