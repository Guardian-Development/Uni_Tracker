package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class StudentTargetCalculatorTests
{
    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedSuccess()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 120
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 50 }
                withResult { resultWeighting = 50 }
            }
            withModule {
                withProperties { moduleCredits = 30 }
                withResult { resultWeighting = 10 }
                withResult { resultWeighting = 50 }
                withResult { resultWeighting = 40 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(33.33, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedAllModulesIncompleteModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 260
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 50 }
                withResult { resultWeighting = 10 }
            }
            withModule {
                withProperties { moduleCredits = 20 }
                withResult { resultWeighting = 30 }
                withResult { resultWeighting = 20 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 10 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(6.54, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedCompleteModulesWithIncompleteModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 240
            }
            withModule {
                withProperties { moduleCredits = 20 } // 12
                withResult { resultWeighting = 40 }
                withResult { resultWeighting = 20 }
            }
            withModule {
                withProperties { moduleCredits = 10 } // 10
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 10 } // 1
                withResult { resultWeighting = 10 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(9.58, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedAllModulesComplete()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 260
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 20 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 220 }
                withResult { resultWeighting = 50 }
                withResult { resultWeighting = 50 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(100.0, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedNotAllCreditsAccountedForInCompleteModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 260
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 20 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 140 }
                withResult { resultWeighting = 50 }
                withResult { resultWeighting = 50 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(69.23, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedZeroCompletedModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 260
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 5 }
            }
            withModule {
                withProperties { moduleCredits = 20 }
                withResult { resultWeighting = 25 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 99 }
            }
            withModule {
                withProperties { moduleCredits = 140 }
                withResult { resultWeighting = 50 }
                withResult { resultWeighting = 0 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(32.85, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedZeroRegisteredModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 100
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(0.0, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedModuleWithNoRegisteredResults()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 133
            }
            withModule {
                withProperties { moduleCredits = 10 }
            }
            withModule {
                withProperties { moduleCredits = 20 }
                withResult { resultWeighting = 27 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultWeighting = 32 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageOfDegreeCreditsCompleted(record)
            assertEquals(6.47, result, 0.001)
        }
    }

    @Test
    fun calculatePercentageOfDegreeCreditsCompletedRegisteredCreditsExceedDegreeAmountThrowsException()
    {
        thrown.expect(IllegalStateException::class.java)
        thrown.expectMessage("Completed credits cannot exceed amount of credits required")

        StudentRecordCalculationTester {
            withConfiguration {
                totalCredits = 200
            }
            withModule {
                withProperties { moduleCredits = 100 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 90 }
                withResult { resultWeighting = 100 }
            }
            withModule {
                withProperties { moduleCredits = 11 }
                withResult { resultWeighting = 100 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            calculator.calculatePercentageOfDegreeCreditsCompleted(record)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsSuccess()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 70.0 }
            }
            withModule {
                withProperties { moduleCredits = 20 }
                withResult { resultPercentage = 70.0 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 70.0 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(70.0, result, 0.001)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsSingleModule()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 40.45 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(40.45, result, 0.001)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsSingleModuleMultipleResults()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 60.0 }
                withResult { resultPercentage = 50.42 }
                withResult { resultPercentage = 22.89 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(44.44, result, 0.001)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsMultipleModulesSingleResult()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 78.87 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 25.29 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 42.31 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(48.82, result, 0.001)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsMultipleModulesMultipleResults()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 78.87 }
                withResult { resultPercentage = 25.29 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 25.29 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 42.31 }
                withResult { resultPercentage = 25.29 }
                withResult { resultPercentage = 25.29 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(37.06, result, 0.001)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsSingleModuleWithNoResultOnly()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(0.0, result, 0.001)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsSomeModulesWithNoResults()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 62.765 }
                withResult { resultPercentage = 98.23 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 25.29 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(62.1, result, 0.01)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsNoModulesRegistered()
    {
        StudentRecordCalculationTester {}.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(0.0, result, 0.01)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsRoundsUpToTwoDecimalPlacesCorrectly()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 61.76 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 61.75 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(61.76, result, 0.01)
        }
    }

    @Test
    fun calculateAverageGradeAchievedInAllRecordedResultsRoundsDownToTwoDecimalPlacesCorrectly()
    {
        StudentRecordCalculationTester {
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 62.764 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
                withResult { resultPercentage = 62.764 }
            }
            withModule {
                withProperties { moduleCredits = 10 }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculateAverageGradeAchievedInAllRecordedResults(record)
            assertEquals(62.76, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetSingleYearSuccess()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 70 //2800
                totalCredits = 40
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 40 //4000
                }
            } //achieved 2082.92 //1000 remaining //required 717.08 //percentage 71.708
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                withResult {
                    resultWeighting = 100 //627.64
                    resultPercentage = 62.764
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 20
                }
                withResult {
                    resultWeighting = 100 //1455.28
                    resultPercentage = 72.764
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(71.71, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetMultipleYearsSuccess()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 65 //7800
                totalCredits = 120 //12000
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 60 //6000
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 60 //6000
                }
            } //achieved 6339.42 //remaining 3500 //required 1460.58 //percentage 41.730857
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30 //3000
                }
                withResult {
                    resultWeighting = 50 //1110
                    resultPercentage = 74.0
                }
                withResult {
                    resultWeighting = 50 //1020
                    resultPercentage = 68.0
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30 //3000
                }
                withResult {
                    resultWeighting = 100 //2182.92
                    resultPercentage = 72.764
                }
            }
            withModule { //2000
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 40 //4000
                }
                withResult {
                    resultWeighting = 50
                    resultPercentage = 84.1 //1682
                }
                withResult {
                    resultWeighting = 0
                    resultPercentage = 91.0
                }
            }
            withModule { //1500
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 20 //2000
                }
                withResult {
                    resultWeighting = 25 //344.5
                    resultPercentage = 68.9
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(41.73, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetNoModulesRegistered()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 70
                totalCredits = 120
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 60
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 60
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(70.0, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetNoResultsRegistered()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 70
                totalCredits = 240
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 120
                }
                withYearWeighting {
                    year = 2
                    weighting = 120
                    creditsCompletedWithinYear = 120
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 40
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 20
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(70.0, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetAchievableTarget()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 65 //15600
                totalCredits = 240 //24000
                withYearWeighting {
                    year = 1
                    weighting = 0
                    creditsCompletedWithinYear = 0
                }
                withYearWeighting {
                    year = 2
                    weighting = 30
                    creditsCompletedWithinYear = 120 //7200
                }
                withYearWeighting {
                    year = 3
                    weighting = 70
                    creditsCompletedWithinYear = 120 //16800
                } //achieved 1795.2 //remaining 21600 //required 13804.8 //percentage 41.730857
            }
            withModule { //0
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30
                }
                withResult {
                    resultWeighting = 100
                    resultPercentage = 72.764
                }
            }
            withModule { //820.8
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 20 //1200
                }
                withResult {
                    resultWeighting = 100
                    resultPercentage = 68.4
                }
            }
            withModule { //974.4
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 20 //1200
                }
                withResult {
                    resultWeighting = 100
                    resultPercentage = 81.2
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(63.91, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetTargetAlreadyAchieved()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 52 //6339.42
                totalCredits = 120 //12000
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 60 //6000
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 60 //6000
                } //9839.42
            } //achieved 6339.42
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30 //3000
                }
                withResult {
                    resultWeighting = 50 //1110
                    resultPercentage = 74.0
                }
                withResult {
                    resultWeighting = 50 //1020
                    resultPercentage = 68.0
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30 //3000
                }
                withResult {
                    resultWeighting = 100 //2182.92
                    resultPercentage = 72.764
                }
            }
            withModule { //2000
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 40 //4000
                }
                withResult {
                    resultWeighting = 50
                    resultPercentage = 84.1 //1682
                }
                withResult {
                    resultWeighting = 0
                    resultPercentage = 91.0
                }
            }
            withModule { //1500
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 20 //2000
                }
                withResult {
                    resultWeighting = 25 //344.5
                    resultPercentage = 68.9
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(0.0, result, 0.01)
        }
    }

    @Test
    fun calculatePercentageRequiredToMeetTargetUnAchievableTarget()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                targetPercentage = 82 //9840
                totalCredits = 120 //12000
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 60 //6000
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 60 //6000
                } //9839.42
            } //achieved 6339.42
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30 //3000
                }
                withResult {
                    resultWeighting = 50 //1110
                    resultPercentage = 74.0
                }
                withResult {
                    resultWeighting = 50 //1020
                    resultPercentage = 68.0
                }
            }
            withModule {
                withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 30 //3000
                }
                withResult {
                    resultWeighting = 100 //2182.92
                    resultPercentage = 72.764
                }
            }
            withModule { //2000
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 40 //4000
                }
                withResult {
                    resultWeighting = 50
                    resultPercentage = 84.1 //1682
                }
                withResult {
                    resultWeighting = 0
                    resultPercentage = 91.0
                }
            }
            withModule { //1500
                withProperties {
                    moduleYearSudied = 2
                    moduleCredits = 20 //2000
                }
                withResult {
                    resultWeighting = 25 //344.5
                    resultPercentage = 68.9
                }
            }
        }.performCalculationOnStudentRecord { record, calculator ->
            val result = calculator.calculatePercentageRequiredToMeetTarget(record)
            assertEquals(Double.POSITIVE_INFINITY, result, 0.01)
        }
    }
}