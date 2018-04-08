package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.roundToTwoDecimalPlaces
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

class StudentTargetCalculator : IProvideStudentTargetCalculations
{
    override fun calculatePercentageRequiredToMeetTarget(studentRecord: StudentRecord): Double
    {
        val totalWeightedCreditsAvailable = totalWeightedCreditsAvailable(
                studentRecord.configuration.yearWeightings)

        val weightedCreditsRequiredToHitTarget = weightedCreditsRequiredToHitTargetPercentage(
                studentRecord.configuration.targetPercentage,
                totalWeightedCreditsAvailable)

        val currentlyAchievedWeightedCredits = currentlyAchievedWeightedCredits(studentRecord)
        val weightedCreditsNeededToHitTarget = weightedCreditsNeededToHitTargetCredits(
                weightedCreditsRequiredToHitTarget,
                currentlyAchievedWeightedCredits)

        val totalWeightedCreditsTaken = totalAmountOfWeightedCreditsTaken(studentRecord)
        val remainingAmountOfWeightedCredits = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCreditsAvailable,
                totalWeightedCreditsTaken)

        val result = percentageRequiredInRemainingWeightedCreditsToHitTarget(
                weightedCreditsNeededToHitTarget,
                remainingAmountOfWeightedCredits)

        return when
        {
            result.isFinite() -> result.roundToTwoDecimalPlaces()
            else -> result
        }
    }

    override fun calculatePercentageOfDegreeCreditsCompleted(studentRecord: StudentRecord): Double
    {
        val amountOfCreditsRequired = studentRecord.configuration.totalCredits
        val completedModuleCredits = studentRecord.modules.values
                .map(::moduleCreditsToModuleCompletionPercentage)
                .map(::creditsCompletedOfModuleBasedOnCompletionPercentage)
                .sum()

        return when {
            completedModuleCredits <= amountOfCreditsRequired ->
                convertCreditsCompletedToPercentage(amountOfCreditsRequired, completedModuleCredits)
            else -> throw IllegalStateException(
                    "Completed credits cannot exceed amount of credits required")
        }
    }

    override fun calculateAverageGradeAchievedInAllRecordedResults(studentRecord: StudentRecord): Double
    {
        return studentRecord.modules.values
                .filter { it.results.isNotEmpty() }
                .flatMap { it.results.values }
                .map { it.resultPercentage }
                .average()
                .takeIf { it.isFinite() }
                ?.roundToTwoDecimalPlaces() ?: 0.0
    }
}
