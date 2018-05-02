package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module

/**
 * Responsible for providing calculations over an individual module.
 */
interface IProvideModuleCalculations
{
    /**
     * Responsible for calculating the completion percentage of a module, based on the amount of credits
     * that have been recorded in the results.
     */
    fun calculatePercentageCompleteOf(module: Module) : Double

    /**
     * Responsible for calculating the average grade of a module, rounded to the nearest int, taking
     * into account result weightings.
     */
    fun calculateCurrentAverageGradeOf(module: Module) : Int
}