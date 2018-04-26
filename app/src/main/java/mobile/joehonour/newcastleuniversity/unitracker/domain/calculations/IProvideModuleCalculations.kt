package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module

interface IProvideModuleCalculations
{
    fun calculatePercentageCompleteOf(module: Module) : Double
    fun calculateCurrentAverageGradeOf(module: Module) : Int
}