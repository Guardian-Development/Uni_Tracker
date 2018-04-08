package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.roundToTwoDecimalPlaces
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

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

fun totalWeightedCreditsAvailable(yearWeightings: List<ConfigurationYearWeighting>) : Double
{
    return yearWeightings.map { it.creditsCompletedWithinYear * it.weighting }.sum().toDouble()
}

fun weightedCreditsRequiredToHitTargetPercentage(targetPercentage: Int,
                                                 totalWeightedCreditsAvailable: Double) : Double
{
    return totalWeightedCreditsAvailable * (targetPercentage.toDouble() / 100)
}

fun currentlyAchievedWeightedCredits(studentRecord: StudentRecord) : Double
{
    return studentRecord.modules.values
            .map { module -> moduleToYearStudied(module, studentRecord) }
            .map { computeWeightedCreditsAchievedWithinModule(it.first, it.second) }
            .sum()
            .takeIf { it.isFinite() } ?: 0.0
}

fun computeWeightedCreditsAchievedWithinModule(module: Module, year: ConfigurationYearWeighting) : Double
{
    val moduleAverageGradePercentage = weightedAverageOfResultsWithinModule(
            module.results.values.toList())

    return when {
        !moduleAverageGradePercentage.isFinite() -> 0.0
        else -> {
            val creditsToModuleCompletionPercentage = moduleCreditsToModuleCompletionPercentage(module)
            val weightedCreditsAvailableForModule = creditsToModuleCompletionPercentage.first * year.weighting
            val completedAmountOfCredits = weightedCreditsAvailableForModule *
                    (creditsToModuleCompletionPercentage.second.toDouble() / 100)

             completedAmountOfCredits * (moduleAverageGradePercentage / 100)
        }
    }
}

fun weightedAverageOfResultsWithinModule(results: List<ModuleResult>) : Double
{
    val totalWeighting = results.map { it.resultWeighting }.sum()
    val result = results.map { it.resultWeighting * it.resultPercentage }.sum()
    return result / totalWeighting
}

fun weightedCreditsNeededToHitTargetCredits(targetWeightedCreditsNeeded: Double,
                                            currentlyAchievedWeightedCredits: Double) : Double
{
    return targetWeightedCreditsNeeded - currentlyAchievedWeightedCredits
}

fun remainingAmountOfWeightedCreditsAvailable(totalWeightedCredits: Double,
                                              amountOfWeightedCreditsTaken: Double) : Double
{
    return totalWeightedCredits - amountOfWeightedCreditsTaken
}

fun totalAmountOfWeightedCreditsTaken(studentRecord: StudentRecord) : Double
{
    return studentRecord.modules.values
            .map { module -> moduleToYearStudied(module, studentRecord) }
            .map { moduleCreditsToModuleCompletionPercentage(it.first) to it.second }
            .map { creditsCompletedOfModuleBasedOnCompletionPercentage(it.first) * it.second.weighting }
            .sum()
            .takeIf { it.isFinite() } ?: 0.0
}

fun percentageRequiredInRemainingWeightedCreditsToHitTarget(
        weightedCreditsNeededToHitTarget: Double,
        remainingAmountOfWeightedCredits: Double) : Double
{
    return when
    {
        weightedCreditsNeededToHitTarget <= 0 -> 0.0
        weightedCreditsNeededToHitTarget > remainingAmountOfWeightedCredits -> Double.POSITIVE_INFINITY
        else -> (weightedCreditsNeededToHitTarget / remainingAmountOfWeightedCredits) * 100
    }
}

private fun moduleToYearStudied(module: Module, studentRecord: StudentRecord) =
        module to studentRecord.configuration.yearWeightings
                .first { it.year == module.moduleYearStudied }
