package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Test

class StudentTargetCalculatorHelperTests
{
    @Test
    fun moduleCreditsToModuleCompletionPercentageSingleResult()
    {
        ModuleCalculationTester {
            withProperties { moduleCredits = 100 }
            withResult { resultWeighting = 50 }
        }.performActionOnModule {
            val result = moduleCreditsToModuleCompletionPercentage(it)
            assertEquals(100 to 50, result)
        }
    }

    @Test
    fun moduleCreditsToModuleCompletionPercentageMultipleResults()
    {
        ModuleCalculationTester {
            withProperties { moduleCredits = 10 }
            withResult { resultWeighting = 50 }
            withResult { resultWeighting = 20 }
            withResult { resultWeighting = 5 }
            withResult { resultWeighting = 5 }
        }.performActionOnModule {
            val result = moduleCreditsToModuleCompletionPercentage(it)
            assertEquals(10 to 80, result)
        }
    }

    @Test
    fun moduleCreditsToModuleCompletionPercentageNoResults()
    {
        ModuleCalculationTester {
            withProperties { moduleCredits = 50 }
        }.performActionOnModule {
            val result = moduleCreditsToModuleCompletionPercentage(it)
            assertEquals(50 to 0, result)
        }
    }

    @Test
    fun moduleCreditsToModuleCompletionPercentageResultsTotallingFullyComplete()
    {
        ModuleCalculationTester {
            withProperties { moduleCredits = 50 }
            withResult { resultWeighting = 30 }
            withResult { resultWeighting = 27 }
            withResult { resultWeighting = 33 }
            withResult { resultWeighting = 10 }
        }.performActionOnModule {
            val result = moduleCreditsToModuleCompletionPercentage(it)
            assertEquals(50 to 100, result)
        }
    }

    @Test
    fun moduleCreditsToModuleCompletionPercentageIncludingResultsWithNoWeighting()
    {
        ModuleCalculationTester {
            withProperties { moduleCredits = 15 }
            withResult { resultWeighting = 30 }
            withResult { resultWeighting = 0 }
            withResult { resultWeighting = 33 }
            withResult { resultWeighting = 1 }
            withResult { resultWeighting = 0 }
        }.performActionOnModule {
            val result = moduleCreditsToModuleCompletionPercentage(it)
            assertEquals(15 to 64, result)
        }
    }

    @Test
    fun creditsCompletedOfModuleBasedOnCompletionPercentagePartiallyCompletedModule()
    {
        val creditsForModule = 10 to 50
        assertEquals(5.0, creditsCompletedOfModuleBasedOnCompletionPercentage(creditsForModule))
    }

    @Test
    fun creditsCompletedOfModuleBasedOnCompletionPercentageFullyCompletedModule()
    {
        val creditsForModule = 15 to 100
        assertEquals(15.0, creditsCompletedOfModuleBasedOnCompletionPercentage(creditsForModule))
    }

    @Test
    fun creditsCompletedOfModuleBasedOnCompletionPercentageZeroCompletedModule()
    {
        val creditsForModule = 20 to 0
        assertEquals(0.0, creditsCompletedOfModuleBasedOnCompletionPercentage(creditsForModule))
    }

    @Test
    fun creditsCompletedOfModuleBasedOnCompletionPercentageNonWholeValue()
    {
        val creditsForModule = 15 to 33
        assertEquals(4.95, creditsCompletedOfModuleBasedOnCompletionPercentage(creditsForModule))
    }

    @Test
    fun convertCreditsCompletedToPercentageWholeNumberResult()
    {
        val totalCredits = 50
        val completedCredits = 25.0
        assertEquals(50.0, convertCreditsCompletedToPercentage(totalCredits, completedCredits), 0.001)
    }

    @Test
    fun convertCreditsCompletedToPercentageZeroCompletedCredits()
    {
        val totalCredits = 23
        val completedCredits = 0.0
        assertEquals(0.0, convertCreditsCompletedToPercentage(totalCredits, completedCredits), 0.001)
    }

    @Test
    fun convertCreditsCompletedToPercentageFullyCompletedCredits()
    {
        val totalCredits = 120
        val completedCredits = 120.0
        assertEquals(100.0, convertCreditsCompletedToPercentage(totalCredits, completedCredits), 0.001)
    }

    @Test
    fun convertCreditsCompletedToPercentageRoundsUpToTwoDecimalPlaces()
    {
        val totalCredits = 100
        val completedCredits = 33.335
        assertEquals(33.34, convertCreditsCompletedToPercentage(totalCredits, completedCredits), 0.001)
    }

    @Test
    fun convertCreditsCompletedToPercentageRoundsDownToTwoDecimalPlaces()
    {
        val totalCredits = 100
        val completedCredits = 25.434
        assertEquals(25.43, convertCreditsCompletedToPercentage(totalCredits, completedCredits), 0.001)
    }

    @Test
    fun totalWeightedCreditsAvailableSingleYear()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 120
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(12000.0, result, 0.01)
        }
    }

    @Test
    fun totalWeightedCreditsAvailableMultipleYears()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 33
                    creditsCompletedWithinYear = 120
                }
                withYearWeighting {
                    year = 1
                    weighting = 66
                    creditsCompletedWithinYear = 120
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(11880.0, result, 0.01)
        }
    }

    @Test
    fun totalWeightedCreditsAvailableNoConfiguredYears()
    {
        StudentRecordCalculationTester {
            withConfiguration {}
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(0.0, result, 0.01)
        }
    }

    @Test
    fun totalWeightedCreditsAvailableSingleYearWithNoWeighting()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 0
                    creditsCompletedWithinYear = 120
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 98
                }
                withYearWeighting {
                    year = 3
                    weighting = 50
                    creditsCompletedWithinYear = 27
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(6250.0, result, 0.01)
        }
    }

    @Test
    fun totalWeightedCreditsAvailableMultipleYearsWithNoWeighting()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 0
                    creditsCompletedWithinYear = 120
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 98
                }
                withYearWeighting {
                    year = 3
                    weighting = 0
                    creditsCompletedWithinYear = 27
                }
                withYearWeighting {
                    year = 4
                    weighting = 50
                    creditsCompletedWithinYear = 60
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(7900.0, result, 0.01)
        }
    }

    @Test
    fun totalWeightedCreditsAvailableSingleYearWithNoCredits()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 0
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 100
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(5000.0, result, 0.01)
        }
    }

    @Test
    fun totalWeightedCreditsAvailableMultipleYearsWithNoCredits()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 0
                }
                withYearWeighting {
                    year = 2
                    weighting = 100
                    creditsCompletedWithinYear = 100
                }
                withYearWeighting {
                    year = 3
                    weighting = 75
                    creditsCompletedWithinYear = 0
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalWeightedCreditsAvailable(studentRecord.configuration.yearWeightings)
            assertEquals(10000.0, result, 0.01)
        }
    }

    @Test
    fun weightedCreditsRequiredToHitTargetPercentageSuccess()
    {
        val targetPercentage = 50
        val totalWeightedCreditsAvailable = 4000.0
        assertEquals(
                2000.0,
                weightedCreditsRequiredToHitTargetPercentage(
                        targetPercentage,
                        totalWeightedCreditsAvailable),
                0.001)
    }

    @Test
    fun weightedCreditsRequiredToHitTargetPercentageOneHundredPercent()
    {
        val targetPercentage = 100
        val totalWeightedCreditsAvailable = 10000.0
        assertEquals(
                10000.0,
                weightedCreditsRequiredToHitTargetPercentage(
                        targetPercentage,
                        totalWeightedCreditsAvailable),
                0.001)
    }

    @Test
    fun weightedCreditsRequiredToHitTargetPercentageFiftyPercent()
    {
        val targetPercentage = 50
        val totalWeightedCreditsAvailable = 10000.0
        assertEquals(
                5000.0,
                weightedCreditsRequiredToHitTargetPercentage(
                        targetPercentage,
                        totalWeightedCreditsAvailable),
                0.001)
    }

    @Test
    fun weightedCreditsRequiredToHitTargetPercentageDecimalResult()
    {
        val targetPercentage = 25
        val totalWeightedCreditsAvailable = 1001.0
        assertEquals(
                250.25,
                weightedCreditsRequiredToHitTargetPercentage(
                        targetPercentage,
                        totalWeightedCreditsAvailable),
                0.001)
    }

    @Test
    fun currentlyAchievedWeightedCreditsSingleYearFullCreditsWithinModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 50
                    }
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 50
                    }
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = currentlyAchievedWeightedCredits(studentRecord)
            assertEquals(4000.0, result, 0.01)
        }
    }

    @Test
    fun currentlyAchievedWeightedCreditsSingleYearPartialCreditsWithinSingleModule()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 23
                    }
                    withResult {
                        resultPercentage = 72.54
                        resultWeighting = 50
                    }
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = currentlyAchievedWeightedCredits(studentRecord)
            assertEquals(3185.4, result, 0.01)
        }
    }

    @Test
    fun currentlyAchievedWeightedCreditsSingleYearPartialCreditsWithinMultipleModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 23
                    }
                    withResult {
                        resultPercentage = 72.54
                        resultWeighting = 50
                    }
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 27.25
                        resultWeighting = 50
                    }
                    withResult {
                        resultPercentage = 63.25
                        resultWeighting = 40
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = currentlyAchievedWeightedCredits(studentRecord)
            assertEquals(1963.9, result, 0.01)
        }
    }

    @Test
    fun currentlyAchievedWeightedCreditsMultipleYearsFullCreditsWithinModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 50
                    creditsCompletedWithinYear = 40
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 40
                }
                withModule { //1000
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 100
                    }
                }
                withModule { //272.5
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 27.25
                        resultWeighting = 100
                    }
                }
                withModule { //1000
                    moduleYearSudied = 2
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = currentlyAchievedWeightedCredits(studentRecord)
            assertEquals(2272.5, result, 0.01)
        }
    }

    @Test
    fun currentlyAchievedWeightedCreditsMultipleYearsPartialCreditsWithinSingleModule()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 30
                    creditsCompletedWithinYear = 40
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 40
                }
                withModule { //600
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 100
                    }
                }
                withModule { //163.5
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 27.25
                        resultWeighting = 100
                    }
                }
                withModule { //750
                    moduleYearSudied = 2
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 75
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = currentlyAchievedWeightedCredits(studentRecord)
            assertEquals(1513.5, result, 0.01)
        }
    }

    @Test
    fun currentlyAchievedWeightedCreditsMultipleYearsPartialCreditsWithinMultipleModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 30
                    creditsCompletedWithinYear = 40
                }
                withYearWeighting {
                    year = 2
                    weighting = 50
                    creditsCompletedWithinYear = 40
                }
                withYearWeighting {
                    year = 3
                    weighting = 20
                    creditsCompletedWithinYear = 40
                }
                withModule { //163.5
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 27.25
                        resultWeighting = 100
                    }
                }
                withModule { //750
                    moduleYearSudied = 2
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 100.0
                        resultWeighting = 75
                    }
                }
                withModule { //216.75
                    moduleYearSudied = 3
                    moduleCredits = 20
                    withResult {
                        resultPercentage = 72.25
                        resultWeighting = 75
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = currentlyAchievedWeightedCredits(studentRecord)
            assertEquals(1130.25, result, 0.01)
        }
    }

    @Test
    fun computeCreditsAchievedWithinModuleFullyCompleted()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 10
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                .withResult {
                    resultWeighting = 100
                    resultPercentage = 100.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(1000.0, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModulePartiallyCompleted()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 10
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                .withResult {
                    resultWeighting = 25
                    resultPercentage = 75.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(187.5, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleMultipleResultsRecorded()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 10
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                .withResult { //187.5
                    resultWeighting = 25
                    resultPercentage = 75.0
                }
                .withResult { //204.6
                    resultWeighting = 33
                    resultPercentage = 62.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(392.1, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleNoResultsRecorded()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 10
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleFullWeightingForYear()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 10
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                .withResult {
                    resultWeighting = 100
                    resultPercentage = 88.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(880.0, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModulePartialWeightingForYear()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 75
                    creditsCompletedWithinYear = 120 //9000
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10 //750
                }
                .withResult {
                    resultWeighting = 75
                    resultPercentage = 60.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(337.5, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleNoWeightingForYear()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 0
                    creditsCompletedWithinYear = 120
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10
                }
                .withResult {
                    resultWeighting = 100
                    resultPercentage = 70.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleResultRecordedFullMarks()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 40
                    creditsCompletedWithinYear = 120 //4800
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10 //400
                }
                .withResult {
                    resultWeighting = 34
                    resultPercentage = 100.0
                }
                .withResult {
                    resultWeighting = 66
                    resultPercentage = 100.0
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(400.0, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleResultRecordedPartialMarks()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 40
                    creditsCompletedWithinYear = 120 //4800
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10 //400
                }
                .withResult {
                    resultWeighting = 34 //91.8816
                    resultPercentage = 67.56
                }
                .withResult {
                    resultWeighting = 66 //64.2048
                    resultPercentage = 24.32
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(156.0864, result, 0.01)
    }

    @Test
    fun computeCreditsAchievedWithinModuleResultRecordedNoMarks()
    {
        val year = ConfigurationYearWeightingBuilder()
                .withProperties {
                    year = 1
                    weighting = 40
                    creditsCompletedWithinYear = 120 //4800
                }.build()
        val module = ModuleBuilder()
                .withProperties {
                    moduleYearSudied = 1
                    moduleCredits = 10 //400
                }
                .withResult {
                    resultWeighting = 34
                    resultPercentage = 0.0
                }
                .withResult {
                    resultWeighting = 66 //64.2048
                    resultPercentage = 24.32
                }
                .build()
        val result = computeWeightedCreditsAchievedWithinModule(module, year)
        assertEquals(64.2048, result, 0.01)
    }

    @Test
    fun weightedAverageOfResultsWithinModuleFullMarks()
    {
        val results = ModuleBuilder()
                .withResult {
                    resultWeighting = 34
                    resultPercentage = 100.0
                }
                .withResult {
                    resultWeighting = 26
                    resultPercentage = 100.0
                }
                .withResult {
                    resultWeighting = 50
                    resultPercentage = 100.0
                }.build().results.values.toList()
        val result = weightedAverageOfResultsWithinModule(results)
        assertEquals(100.0, result, 0.01)
    }

    @Test
    fun weightedAverageOfResultsWithinModulePartialMarks()
    {
        val results = ModuleBuilder()
                .withResult {
                    resultWeighting = 34
                    resultPercentage = 51.0
                }
                .withResult {
                    resultWeighting = 26
                    resultPercentage = 84.0
                }
                .withResult {
                    resultWeighting = 50
                    resultPercentage = 72.0 //7518
                }.build().results.values.toList()
        val result = weightedAverageOfResultsWithinModule(results)
        assertEquals(68.345, result, 0.01)
    }

    @Test
    fun weightedAverageOfResultsWithinModuleNoMarks()
    {
        val result = weightedAverageOfResultsWithinModule(emptyList())
        assertEquals(Double.NaN, result, 0.01)
    }

    @Test
    fun weightedAverageOfResultsWithinModuleFullWeightedResult()
    {
        val results = ModuleBuilder()
                .withResult {
                    resultWeighting = 100
                    resultPercentage = 51.0
                }
                .withResult {
                    resultWeighting = 0
                    resultPercentage = 72.0
                }.build().results.values.toList()
        val result = weightedAverageOfResultsWithinModule(results)
        assertEquals(51.0, result, 0.01)
    }

    @Test
    fun weightedAverageOfResultsWithinModulePartialWeightedResult()
    {
        val results = ModuleBuilder()
                .withResult {
                    resultWeighting = 67
                    resultPercentage = 51.0
                }
                .withResult {
                    resultWeighting = 12
                    resultPercentage = 72.0
                }.build().results.values.toList() //4281
        val result = weightedAverageOfResultsWithinModule(results)
        assertEquals(54.189, result, 0.01)
    }

    @Test
    fun weightedAverageOfResultsWithinModuleZeroWeightedResult()
    {
        val results = ModuleBuilder()
                .withResult {
                    resultWeighting = 0
                    resultPercentage = 51.0
                }
                .withResult {
                    resultWeighting = 0
                    resultPercentage = 72.0
                }.build().results.values.toList()
        val result = weightedAverageOfResultsWithinModule(results)
        assertEquals(Double.NaN, result, 0.01)
    }

    @Test
    fun weightedCreditsNeededToHitTargetCreditsPositiveNeeded()
    {
        val targetWeightedCreditsNeeded = 4000.0
        val currentlyAchievedWeightedCredits = 2000.0
        val result = weightedCreditsNeededToHitTargetCredits(
                targetWeightedCreditsNeeded, currentlyAchievedWeightedCredits)

        assertEquals(2000.0, result, 0.01)
    }

    @Test
    fun weightedCreditsNeededToHitTargetCreditsDecimalAmountNeeded()
    {
        val targetWeightedCreditsNeeded = 2378.892
        val currentlyAchievedWeightedCredits = 2000.678
        val result = weightedCreditsNeededToHitTargetCredits(
                targetWeightedCreditsNeeded, currentlyAchievedWeightedCredits)

        assertEquals(378.214, result, 0.01)
    }

    @Test
    fun weightedCreditsNeededToHitTargetCreditsNoneNeeded()
    {
        val targetWeightedCreditsNeeded = 10000.892
        val currentlyAchievedWeightedCredits = 10000.892
        val result = weightedCreditsNeededToHitTargetCredits(
                targetWeightedCreditsNeeded, currentlyAchievedWeightedCredits)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun weightedCreditsNeededToHitTargetCreditsNegativeNeeded()
    {
        val targetWeightedCreditsNeeded = 5000.0
        val currentlyAchievedWeightedCredits = 5001.0
        val result = weightedCreditsNeededToHitTargetCredits(
                targetWeightedCreditsNeeded, currentlyAchievedWeightedCredits)

        assertEquals(-1.0, result, 0.01)
    }

    @Test
    fun remainingAmountOfWeightedCreditsAvailablePositiveAvailable()
    {
        val totalWeightedCredits = 1000.0
        val amountOfWeightedCreditsTaken = 500.0
        val result = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCredits, amountOfWeightedCreditsTaken)

        assertEquals(500.0, result, 0.01)
    }

    @Test
    fun remainingAmountOfWeightedCreditsAvailableDecimalAmountAvailable()
    {
        val totalWeightedCredits = 1000.0
        val amountOfWeightedCreditsTaken = 500.525
        val result = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCredits, amountOfWeightedCreditsTaken)

        assertEquals(499.475, result, 0.01)
    }

    @Test
    fun remainingAmountOfWeightedCreditsAvailableNoneAvailable()
    {
        val totalWeightedCredits = 578.25
        val amountOfWeightedCreditsTaken = 578.25
        val result = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCredits, amountOfWeightedCreditsTaken)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun remainingAmountOfWeightedCreditsAvailableNegativeAvailable()
    {
        val totalWeightedCredits = 578.25
        val amountOfWeightedCreditsTaken = 578.26
        val result = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCredits, amountOfWeightedCreditsTaken)

        assertEquals(-0.01, result, 0.01)
    }

    @Test
    fun remainingAmountOfWeightedCreditsAvailableZeroCreditsTaken()
    {
        val totalWeightedCredits = 578.25
        val amountOfWeightedCreditsTaken = 0.0
        val result = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCredits, amountOfWeightedCreditsTaken)

        assertEquals(578.25, result, 0.01)
    }

    @Test
    fun remainingAmountOfWeightedCreditsTotalWeightedCreditsZero()
    {
        val totalWeightedCredits = 0.0
        val amountOfWeightedCreditsTaken = 0.0
        val result = remainingAmountOfWeightedCreditsAvailable(
                totalWeightedCredits, amountOfWeightedCreditsTaken)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenCompletedAllModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 100
                    }
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(4000.0, result, 0.01)
        }
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenSingleYear()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20 //1500
                    withResult {
                        resultWeighting = 50
                    }
                    withResult {
                        resultWeighting = 25
                    }
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20 //1900
                    withResult {
                        resultWeighting = 95
                    }
                    withResult {
                        resultWeighting = 0
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(3400.0, result, 0.01)
        }
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenMultipleYears()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 75
                    creditsCompletedWithinYear = 40
                }
                withYearWeighting {
                    year = 2
                    weighting = 25
                    creditsCompletedWithinYear = 20
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 40 //2250
                    withResult {
                        resultWeighting = 50
                    }
                    withResult {
                        resultWeighting = 25
                    }
                }
                withModule {
                    moduleYearSudied = 2 //190
                    moduleCredits = 10
                    withResult {
                        resultWeighting = 76
                    }
                }
                withModule {
                    moduleYearSudied = 2 //250
                    moduleCredits = 10
                    withResult {
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(2690.0, result, 0.01)
        }
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenPartiallyCompletedModules()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 22
                    creditsCompletedWithinYear = 40
                }
                withYearWeighting {
                    year = 2
                    weighting = 65
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20 //330
                    withResult {
                        resultWeighting = 50
                    }
                    withResult {
                        resultWeighting = 25
                    }
                }
                withModule {
                    moduleYearSudied = 2
                    moduleCredits = 20 //689
                    withResult {
                        resultWeighting = 53
                    }
                    withResult {
                        resultWeighting = 0
                    }
                }
                withModule {
                    moduleYearSudied = 2
                    moduleCredits = 20 //767
                    withResult {
                        resultWeighting = 27
                    }
                    withResult {
                        resultWeighting = 32
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(1786.0, result, 0.01)
        }
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenFullyWeightedYear()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 100
                    creditsCompletedWithinYear = 20
                }
                withYearWeighting {
                    year = 2
                    weighting = 0
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 50
                    }
                    withResult {
                        resultWeighting = 25
                    }
                }
                withModule {
                    moduleYearSudied = 2
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(1500.0, result, 0.01)
        }
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenPartiallyWeightedYear()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 33
                    creditsCompletedWithinYear = 20
                }
                withYearWeighting {
                    year = 2
                    weighting = 67
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1 //495
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 50
                    }
                    withResult {
                        resultWeighting = 25
                    }
                }
                withModule {
                    moduleYearSudied = 2 //1340
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(1835.0, result, 0.01)
        }
    }

    @Test
    fun totalAmountOfWeightedCreditsTakenZeroWeightedYear()
    {
        StudentRecordCalculationTester {
            withConfiguration {
                withYearWeighting {
                    year = 1
                    weighting = 0
                    creditsCompletedWithinYear = 20
                }
                withYearWeighting {
                    year = 2
                    weighting = 0
                    creditsCompletedWithinYear = 40
                }
                withModule {
                    moduleYearSudied = 1
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 50
                    }
                    withResult {
                        resultWeighting = 25
                    }
                }
                withModule {
                    moduleYearSudied = 2
                    moduleCredits = 20
                    withResult {
                        resultWeighting = 100
                    }
                }
            }
        }.performCalculationOnStudentRecord { studentRecord, _ ->
            val result = totalAmountOfWeightedCreditsTaken(studentRecord)
            assertEquals(0.0, result, 0.01)
        }
    }

    @Test
    fun percentageRequiredInRemainingWeightedCreditsToHitTargetPositiveRequired()
    {
        val weightedCreditsRequired = 250.0
        val weightedCreditsRemaining = 500.0
        val result = percentageRequiredInRemainingWeightedCreditsToHitTarget(
                weightedCreditsRequired, weightedCreditsRemaining)

        assertEquals(50.0, result, 0.01)
    }

    @Test
    fun percentageRequiredInRemainingWeightedCreditsToHitTargetZeroRequired()
    {
        val weightedCreditsRequired = 0.0
        val weightedCreditsRemaining = 5000.0
        val result = percentageRequiredInRemainingWeightedCreditsToHitTarget(
                weightedCreditsRequired, weightedCreditsRemaining)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun percentageRequiredInRemainingWeightedCreditsToHitTargetZeroRequiredZeroRemaining()
    {
        val weightedCreditsRequired = 0.0
        val weightedCreditsRemaining = 0.0
        val result = percentageRequiredInRemainingWeightedCreditsToHitTarget(
                weightedCreditsRequired, weightedCreditsRemaining)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun percentageRequiredInRemainingWeightedCreditsToHitTargetNegativeRequired()
    {
        val weightedCreditsRequired = -250.0
        val weightedCreditsRemaining = 5000.0
        val result = percentageRequiredInRemainingWeightedCreditsToHitTarget(
                weightedCreditsRequired, weightedCreditsRemaining)

        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun percentageRequiredInRemainingWeightedCreditsToHitTargetNotPossibleToAchieve()
    {
        val weightedCreditsRequired = 5000.0
        val weightedCreditsRemaining = 4999.0
        val result = percentageRequiredInRemainingWeightedCreditsToHitTarget(
                weightedCreditsRequired, weightedCreditsRemaining)

        assertEquals(Double.POSITIVE_INFINITY, result)
    }
}