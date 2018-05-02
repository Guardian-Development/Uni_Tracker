package mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideStudentTargetCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

/**
 * The view model is responsible for presenting information to the dashboard view.
 *
 * @param dataAccess provides functionality to read data from the database.
 * @param authProvider provides functionality to query about the users authentication status.
 * @param studentTargetCalculator provides functionality to calculate results over the students
 * modules and results.
 */
class DashboardViewModel(private val dataAccess: IProvideDataAccess,
                         private val authProvider: IProvideAuthentication,
                         private val studentTargetCalculator: IProvideStudentTargetCalculations) : ViewModel()
{
    val percentageRequiredToMeetTarget: MutableLiveData<Double> = MutableLiveData()
    val percentageOfDegreeCreditsCompleted: MutableLiveData<Double> = MutableLiveData()
    val averageGradeAchievedInAllRecordedResults: MutableLiveData<Double> = MutableLiveData()

    /**
     * Responsible for calculating the percentage required by a student to meet their configured
     * target.
     *
     * @param onError is executed if an error is encountered while calculating the result
     * @param onSuccess is executed if the calculation is performed successfully, passing the result.
     */
    fun calculatePercentageRequiredToMeetTarget(onError: (String?) -> Unit,
                                                onSuccess: (Double) -> Unit) =
        performCalculationOnStudentProgress(
                onError,
                onSuccess,
                studentTargetCalculator::calculatePercentageRequiredToMeetTarget)

    /**
     * Responsible for calculating the percentage of degree credits completed by a student.
     *
     * @param onError is executed if an error is encountered while calculating the result
     * @param onSuccess is executed if the calculation is performed successfully, passing the result.
     */
    fun calculatePercentageOfDegreeCreditsCompleted(onError: (String?) -> Unit,
                                                    onSuccess: (Double) -> Unit) =
            performCalculationOnStudentProgress(
                    onError,
                    onSuccess,
                    studentTargetCalculator::calculatePercentageOfDegreeCreditsCompleted)

    /**
     * Responsible for calculating the average grade achieved by a student in all their results.
     *
     * @param onError is executed if an error is encountered while calculating the result
     * @param onSuccess is executed if the calculation is performed successfully, passing the result.
     */
    fun calculateAverageGradeAchievedInAllRecordedResults(onError: (String?) -> Unit,
                                                          onSuccess: (Double) -> Unit) =
            performCalculationOnStudentProgress(
                    onError,
                    onSuccess,
                    studentTargetCalculator::calculateAverageGradeAchievedInAllRecordedResults)

    /**
     * Responsible for performing a calculation on a students record.
     * If the student is not logged in then onError is immediately called.
     *
     * @param onError is executed if an error is encountered while calculating the result
     * @param onSuccess is executed if the calculation is performed successfully, passing the result.
     * @param calculation is executed over the student record.
     */
    private fun performCalculationOnStudentProgress(onError: (String?) -> Unit,
                                                    onSuccess: (Double) -> Unit,
                                                    calculation: (StudentRecord) -> Double)
    {
        when(authProvider.userLoggedIn)
        {
            true -> {
                dataAccess.readItemFromDatabase(
                        DataLocationKeys.studentRecordLocation(authProvider.userUniqueId!!),
                        StudentRecord::class.java,
                        onError)
                {
                    try
                    {
                        onSuccess(calculation(it))
                    }
                    catch (e: Exception) { onError(e.message) }
                }
            }
            false -> onError("User not logged in")
        }
    }
}