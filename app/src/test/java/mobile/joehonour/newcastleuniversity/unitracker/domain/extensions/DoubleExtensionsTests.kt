package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class DoubleExtensionsTests
{
    @Test
    fun roundTo2DecimalPlacesRoundsUpCorrectly()
    {
        val double = 1.12524
        assertEquals(1.13, double.roundToTwoDecimalPlaces(), 0.001)
    }

    @Test
    fun roundTo2DecimalPlacesRoundsDownCorrectly()
    {
        val double = 9.83457
        assertEquals(9.83, double.roundToTwoDecimalPlaces(), 0.001)
    }

    @Test
    fun roundTo2DecimalPlacesNoRoundingRequiredCorrectly()
    {
        val double = 9.8
        assertEquals(9.80, double.roundToTwoDecimalPlaces(), 0.001)
    }
}