package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

class StudentTargetCalculator : IProvideStudentTargetCalculations
{
    override fun calculatePercentageRequiredToMeetTarget(studentRecord: StudentRecord): Double
    {
        return 10.0
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
        return 12.0
    }

}
