package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

/**
 * Provides calculations over an entire student record.
 */
interface IProvideStudentTargetCalculations
{
    /**
     * Responsible for calculating the percentage required by a student to meet their currently
     * configured target percentage.
     *
     * This takes into account result weightings along with partially completed modules. It uses
     * a system of converting everything into weighted credits in order to work out how many credits
     * a user currently ha achieved along with how many they have remaining that are up for grabs.
     */
    fun calculatePercentageRequiredToMeetTarget(studentRecord: StudentRecord) : Double

    /**
     * Responsible for calculating the percentage of the students degree they have completed.
     *
     * This takes into account partially completed modules, in order to determine the exact amount of
     * credits the user has accounted for in their results, and how many are still remaining.
     */
    fun calculatePercentageOfDegreeCreditsCompleted(studentRecord: StudentRecord) : Double

    /**
     * Responsible for calculating the average grade achieved in all results the user has ever entered.
     * This is not a weighted grade, but merely a mean average.
     */
    fun calculateAverageGradeAchievedInAllRecordedResults(studentRecord: StudentRecord) : Double
}