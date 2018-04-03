package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

interface IProvideStudentTargetCalculations
{
    fun calculatePercentageRequiredToMeetTarget(studentRecord: StudentRecord) : Double
    fun calculatePercentageOfDegreeCreditsCompleted(studentRecord: StudentRecord) : Double
    fun calculateAverageGradeAchievedInAllRecordedResults(studentRecord: StudentRecord) : Double
}