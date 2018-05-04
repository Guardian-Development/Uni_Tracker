package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import junit.framework.Assert
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ModuleCalculatorTests
{
    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()

    private fun moduleWithResults(results: List<ModuleResult>) : Module
            = Module("id", "test", "test", 10, 1, results.map { it.resultId to it }.toMap())

    @Test
    fun calculatePercentageCompleteSingleResultSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(ModuleResult("1", "name", 50, 82.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(50.0, calculator.calculatePercentageCompleteOf(module))
    }

    @Test
    fun calculatePercentageCompleteNoResultsSuccess()
    {
        val calculator = ModuleCalculator()
        val module = moduleWithResults(emptyList())
        Assert.assertEquals(0.0, calculator.calculatePercentageCompleteOf(module))
    }

    @Test
    fun calculatePercentageCompleteMultipleResultsSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 50, 82.5),
                ModuleResult("2", "name", 10, 82.5),
                ModuleResult("3", "name", 20, 82.5),
                ModuleResult("4", "name", 5, 82.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(85.0, calculator.calculatePercentageCompleteOf(module))
    }

    @Test
    fun calculatePercentageCompleteResultWithZeroWeightingSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 0, 82.5),
                ModuleResult("2", "name", 10, 82.5),
                ModuleResult("3", "name", 0, 82.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(10.0, calculator.calculatePercentageCompleteOf(module))
    }

    @Test
    fun calculatePercentageCompleteResultsSumToOneHundredPercentCompletionSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 50, 82.5),
                ModuleResult("2", "name", 10, 82.5),
                ModuleResult("3", "name", 20, 82.5),
                ModuleResult("4", "name", 5, 82.5),
                ModuleResult("5", "name", 10, 82.5),
                ModuleResult("6", "name", 5, 82.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(100.0, calculator.calculatePercentageCompleteOf(module))
    }

    @Test
    fun calculatePercentageCompleteSingleResultOverOneHundredPercentCompletionFailure()
    {
        thrown.expect(IllegalStateException::class.java)
        thrown.expectMessage("Cannot have results totalling more than 100% for a module")

        val calculator = ModuleCalculator()
        val results = listOf(ModuleResult("1", "name", 101, 82.5))
        val module = moduleWithResults(results)
        calculator.calculatePercentageCompleteOf(module)
    }

    @Test
    fun calculatePercentageCompleteMultipleResultsSumToOverOneHundredPercentCompletionFailure()
    {
        thrown.expect(IllegalStateException::class.java)
        thrown.expectMessage("Cannot have results totalling more than 100% for a module")

        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 50, 82.5),
                ModuleResult("2", "name", 10, 82.5),
                ModuleResult("3", "name", 20, 82.5),
                ModuleResult("4", "name", 5, 82.5),
                ModuleResult("5", "name", 10, 82.5),
                ModuleResult("6", "name", 6, 82.5))
        val module = moduleWithResults(results)
        calculator.calculatePercentageCompleteOf(module)
    }

    @Test
    fun calculateCurrentAverageGradeSingleResultSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(ModuleResult("1", "name", 50, 82.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(83, calculator.calculateCurrentAverageGradeOf(module))
    }

    @Test
    fun calculateCurrentAverageGradeNoResultsSuccess()
    {
        val calculator = ModuleCalculator()
        val module = moduleWithResults(emptyList())
        Assert.assertEquals(0, calculator.calculateCurrentAverageGradeOf(module))
    }

    @Test
    fun calculateCurrentAverageGradeMultipleResultsSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 50, 68.10),
                ModuleResult("2", "name", 10, 72.98),
                ModuleResult("3", "name", 20, 65.71),
                ModuleResult("4", "name", 5, 22.29))
        val module = moduleWithResults(results)
        Assert.assertEquals(65, calculator.calculateCurrentAverageGradeOf(module))
    }

    @Test
    fun calculateCurrentAverageGradeMultipleIdenticalPercentageResultsSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 50, 82.5),
                ModuleResult("2", "name", 10, 82.5),
                ModuleResult("3", "name", 20, 82.5),
                ModuleResult("4", "name", 5, 82.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(83, calculator.calculateCurrentAverageGradeOf(module))
    }

    @Test
    fun calculateCurrentAverageGradeRoundsUpToTwoDecimalPlacesSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 10, 75.5),
                ModuleResult("2", "name", 10, 75.5),
                ModuleResult("3", "name", 10, 75.54),
                ModuleResult("4", "name", 10, 75.54))
        val module = moduleWithResults(results)
        Assert.assertEquals(76, calculator.calculateCurrentAverageGradeOf(module))
    }

    @Test
    fun calculateCurrentAverageGradeRoundsDownToNearestIntegerSuccess()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 20, 75.5),
                ModuleResult("2", "name", 20, 75.5),
                ModuleResult("3", "name", 20, 75.4),
                ModuleResult("4", "name", 20, 75.4),
                ModuleResult("5", "name", 20, 75.4))
        val module = moduleWithResults(results)
        Assert.assertEquals(75, calculator.calculateCurrentAverageGradeOf(module))
    }

    @Test
    fun calculateCurrentAverageGradeTakesIntoAccountWeightings()
    {
        val calculator = ModuleCalculator()
        val results = listOf(
                ModuleResult("1", "name", 50, 62.15),
                ModuleResult("2", "name", 20, 78.2),
                ModuleResult("3", "name", 30, 51.5))
        val module = moduleWithResults(results)
        Assert.assertEquals(62, calculator.calculateCurrentAverageGradeOf(module))
    }
}