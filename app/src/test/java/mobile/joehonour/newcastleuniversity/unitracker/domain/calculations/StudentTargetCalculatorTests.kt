package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import junit.framework.Assert.assertEquals
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
}