package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import junit.framework.Assert.assertEquals
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
}