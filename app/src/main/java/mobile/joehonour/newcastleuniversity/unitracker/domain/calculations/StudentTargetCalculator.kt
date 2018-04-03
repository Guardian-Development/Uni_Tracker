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
        return 30.0
    }

    override fun calculateAverageGradeAchievedInAllRecordedResults(studentRecord: StudentRecord): Double
    {
        return 12.0
    }

}
