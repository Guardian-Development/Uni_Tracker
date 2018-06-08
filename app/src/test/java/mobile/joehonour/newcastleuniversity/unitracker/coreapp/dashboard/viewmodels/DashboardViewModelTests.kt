package mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.viewmodels

import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideStudentTargetCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataSingleReadAccess
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import org.junit.Test

class DashboardViewModelTests
{
    @Test
    fun calculatePercentageRequiredToMeetTargetSuccess()
    {
        val onError = mock<(String?) -> Unit>()
        val onSuccess = mock<(Double) -> Unit> {
            on { invoke(10.0) } doAnswer { FieldAssert(10.0).doAssert(it.arguments[0] as Double) }
        }

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[3] as (StudentRecord) -> Unit).invoke(mock())
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val calculator = mock<IProvideStudentTargetCalculations> {
            on { calculatePercentageRequiredToMeetTarget(any()) } doReturn 10.0
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, calculator)

        viewModel.calculatePercentageRequiredToMeetTarget(onError, onSuccess)

        verify(onSuccess).invoke(10.0)
        verifyZeroInteractions(onError)
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetUserNotLoggedInError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("User not logged in").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val viewModel = DashboardViewModel(mock(), authProvider, mock())

        viewModel.calculatePercentageRequiredToMeetTarget(onError, onSuccess)

        verify(onError).invoke("User not logged in")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetDatabaseReadFailCausesError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Data read failed").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[2] as (String?) -> Unit).invoke("Data read failed")
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, mock())

        viewModel.calculatePercentageRequiredToMeetTarget(onError, onSuccess)

        verify(onError).invoke("Data read failed")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetCalculationFailsCausesError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Failure during calculation").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[3] as (StudentRecord) -> Unit).invoke(mock())
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val calculator = mock<IProvideStudentTargetCalculations> {
            on { calculatePercentageRequiredToMeetTarget(any()) } doAnswer {
                throw Exception("Failure during calculation")
            }
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, calculator)

        viewModel.calculatePercentageRequiredToMeetTarget(onError, onSuccess)

        verify(onError).invoke("Failure during calculation")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedSuccess()
    {
        val onError = mock<(String?) -> Unit>()
        val onSuccess = mock<(Double) -> Unit> {
            on { invoke(10.0) } doAnswer { FieldAssert(10.0).doAssert(it.arguments[0] as Double) }
        }

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[3] as (StudentRecord) -> Unit).invoke(mock())
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val calculator = mock<IProvideStudentTargetCalculations> {
            on { calculatePercentageOfDegreeCreditsCompleted(any()) } doReturn 10.0
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, calculator)

        viewModel.calculatePercentageOfDegreeCreditsCompleted(onError, onSuccess)

        verify(onSuccess).invoke(10.0)
        verifyZeroInteractions(onError)
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedUserNotLoggedInError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("User not logged in").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val viewModel = DashboardViewModel(mock(), authProvider, mock())

        viewModel.calculatePercentageOfDegreeCreditsCompleted(onError, onSuccess)

        verify(onError).invoke("User not logged in")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedDatabaseReadFailCausesError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Data read failed").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[2] as (String?) -> Unit).invoke("Data read failed")
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, mock())

        viewModel.calculatePercentageOfDegreeCreditsCompleted(onError, onSuccess)

        verify(onError).invoke("Data read failed")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedCalculationFailsCausesError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Failure during calculation").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[3] as (StudentRecord) -> Unit).invoke(mock())
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val calculator = mock<IProvideStudentTargetCalculations> {
            on { calculatePercentageOfDegreeCreditsCompleted(any()) } doAnswer {
                throw Exception("Failure during calculation")
            }
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, calculator)

        viewModel.calculatePercentageOfDegreeCreditsCompleted(onError, onSuccess)

        verify(onError).invoke("Failure during calculation")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsSuccess()
    {
        val onError = mock<(String?) -> Unit>()
        val onSuccess = mock<(Double) -> Unit> {
            on { invoke(10.0) } doAnswer { FieldAssert(10.0).doAssert(it.arguments[0] as Double) }
        }

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[3] as (StudentRecord) -> Unit).invoke(mock())
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val calculator = mock<IProvideStudentTargetCalculations> {
            on { calculateAverageGradeAchievedInAllRecordedResults(any()) } doReturn 10.0
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, calculator)

        viewModel.calculateAverageGradeAchievedInAllRecordedResults(onError, onSuccess)

        verify(onSuccess).invoke(10.0)
        verifyZeroInteractions(onError)
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsUserNotLoggedInError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("User not logged in").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val viewModel = DashboardViewModel(mock(), authProvider, mock())

        viewModel.calculateAverageGradeAchievedInAllRecordedResults(onError, onSuccess)

        verify(onError).invoke("User not logged in")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsDatabaseReadFailCausesError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Data read failed").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[2] as (String?) -> Unit).invoke("Data read failed")
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, mock())

        viewModel.calculateAverageGradeAchievedInAllRecordedResults(onError, onSuccess)

        verify(onError).invoke("Data read failed")
        verifyZeroInteractions(onSuccess)
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsCalculationFailsCausesError()
    {
        val onError = mock<(String?) -> Unit> {
            on { invoke(any()) } doAnswer {
                FieldAssert("Failure during calculation").doAssert(it.arguments[0] as String)
            }
        }
        val onSuccess = mock<(Double) -> Unit>()

        val dataAccess = mock<IProvideDataSingleReadAccess> {
            on { readItemFromDatabase<StudentRecord>(eq("userid/"), any(), any(), any()) } doAnswer {
                (it.arguments[3] as (StudentRecord) -> Unit).invoke(mock())
            }
        }

        val authProvider = mock<IProvideAuthentication> {
            on { userUniqueId } doReturn "userid"
            on { userLoggedIn } doReturn true
        }

        val calculator = mock<IProvideStudentTargetCalculations> {
            on { calculateAverageGradeAchievedInAllRecordedResults(any()) } doAnswer {
                throw Exception("Failure during calculation")
            }
        }

        val viewModel = DashboardViewModel(dataAccess, authProvider, calculator)

        viewModel.calculateAverageGradeAchievedInAllRecordedResults(onError, onSuccess)

        verify(onError).invoke("Failure during calculation")
        verifyZeroInteractions(onSuccess)
    }
}