package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NullHelperExtensionsTests {

    @Test
    fun nullableNotNullFalse() {
        val int: Int? = null
        assertFalse(int.notNull())
    }

    @Test
    fun nullableNotNullTrue() {
        val double: Double? = 0.0
        assertTrue(double.notNull())
    }

    @Test
    fun objectNotNullTrue() {
        val double = 0.0
        assertTrue(double.notNull())
    }
}