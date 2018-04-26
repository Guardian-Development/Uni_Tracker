package mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideStudentTargetCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

class DashboardViewModel(private val dataAccess: IProvideDataAccess,
                         private val authProvider: IProvideAuthentication,
                         private val studentTargetCalculator: IProvideStudentTargetCalculations) : ViewModel()
{
    val percentageRequiredToMeetTarget: MutableLiveData<Double> = MutableLiveData()
    val percentageOfDegreeCreditsCompleted: MutableLiveData<Double> = MutableLiveData()
    val averageGradeAchievedInAllRecordedResults: MutableLiveData<Double> = MutableLiveData()

    fun calculatePercentageRequiredToMeetTarget(onError: (String?) -> Unit,
                                                onSuccess: (Double) -> Unit) =
        performCalculationOnStudentProgress(
                onError,
                onSuccess,
                studentTargetCalculator::calculatePercentageRequiredToMeetTarget)

    fun calculatePercentageOfDegreeCreditsCompleted(onError: (String?) -> Unit,
                                                    onSuccess: (Double) -> Unit) =
            performCalculationOnStudentProgress(
                    onError,
                    onSuccess,
                    studentTargetCalculator::calculatePercentageOfDegreeCreditsCompleted)

    fun calculateAverageGradeAchievedInAllRecordedResults(onError: (String?) -> Unit,
                                                          onSuccess: (Double) -> Unit) =
            performCalculationOnStudentProgress(
                    onError,
                    onSuccess,
                    studentTargetCalculator::calculateAverageGradeAchievedInAllRecordedResults)

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