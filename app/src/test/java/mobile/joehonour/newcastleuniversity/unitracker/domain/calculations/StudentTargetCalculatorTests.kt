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
}