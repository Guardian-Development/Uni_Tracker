package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.roundToTwoDecimalPlaces
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module


fun moduleCreditsToModuleCompletionPercentage(module: Module) : Pair<Int, Int>
{
    return module.moduleCredits to module.results.values.map { it.resultWeighting }.sum()
}

fun creditsCompletedOfModuleBasedOnCompletionPercentage(
        creditsToCompletionPercentage: Pair<Int, Int>) : Double
{
    return creditsToCompletionPercentage.first.toDouble() *
            (creditsToCompletionPercentage.second.toDouble() / 100)
}

fun convertCreditsCompletedToPercentage(totalCredits: Int, completedCredits: Double) : Double
{
    val total = (completedCredits / totalCredits) * 100
    return total.roundToTwoDecimalPlaces()
}
