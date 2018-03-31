package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NullHelperExtensionsTests
{
    @Test
    fun notNullDetectsNullableNull()
    {
        val int: Int? = null
        assertFalse(int.notNull())
    }

    @Test
    fun notNullDetectsNullableNotNull()
    {
        val double: Double? = 0.0
        assertTrue(double.notNull())
    }

    @Test
    fun notNullDetectsNotNullable()
    {
        val double = 0.0
        assertTrue(double.notNull())
    }

    @Test
    fun notNullOrEmptyDetectsNull()
    {
        val string: String? = null
        assertFalse(string.notNullOrEmpty())
    }

    @Test
    fun notNullOrEmptyDetectsEmpty()
    {
        val string = ""
        assertFalse(string.notNullOrEmpty())
    }

    @Test
    fun notNullOrEmptyDetectsNotEmpty()
    {
        val string = " "
        assertTrue(string.notNullOrEmpty())
    }

    @Test
    fun notNullWithinInclusiveRangeDetectsNull()
    {
        val int: Int? = null
        assertFalse(int.notNullWithinInclusiveRange(0, 10))
    }

    @Test
    fun notNullWithinInclusiveRangeDetectsMinAllowedValue()
    {
        val int: Int? = 10
        assertTrue(int.notNullWithinInclusiveRange(10, 15))
    }

    @Test
    fun notNullWithinInclusiveRangeDetectsMaxAllowedValue()
    {
        val int: Int? = 50
        assertTrue(int.notNullWithinInclusiveRange(30, 50))
    }

    @Test
    fun notNullWithinInclusiveRangeDetectsBelowMinAllowedValue()
    {
        val int: Int? = 174
        assertFalse(int.notNullWithinInclusiveRange(175, 250))
    }

    @Test
    fun notNullWithinInclusiveRangeDetectsAboveMaxAllowedValue()
    {
        val int: Int? = 45
        assertFalse(int.notNullWithinInclusiveRange(24, 44))
    }

    @Test
    fun notNullDoubleWithinInclusiveRangeDetectsNull()
    {
        val double: Double? = null
        assertFalse(double.notNullWithinInclusiveRange(0.0, 10.0))
    }

    @Test
    fun notNullDoubleWithinInclusiveRangeDetectsMinAllowedValue()
    {
        val double: Double? = 10.1
        assertTrue(double.notNullWithinInclusiveRange(10.1, 15.0))
    }

    @Test
    fun notNullDoubleWithinInclusiveRangeDetectsMaxAllowedValue()
    {
        val double: Double? = 50.78
        assertTrue(double.notNullWithinInclusiveRange(30.0, 50.78))
    }

    @Test
    fun notNullDoubleWithinInclusiveRangeDetectsBelowMinAllowedValue()
    {
        val double: Double? = 174.99
        assertFalse(double.notNullWithinInclusiveRange(175.0, 250.0))
    }

    @Test
    fun notNullDoubleWithinInclusiveRangeDetectsAboveMaxAllowedValue()
    {
        val double: Double? = 45.01
        assertFalse(double.notNullWithinInclusiveRange(24.89, 45.0))
    }

    @Test
    fun executeIfNotNullOrEmptyDetectsNullAndExecutesCorrectly()
    {
        val ifNullMock = mock<() -> Unit>()
        val notNullMock = mock<(String) -> Unit>()
        val string: String? = null

        string.executeIfNotNullOrEmpty(ifNullMock, notNullMock)

        verify(ifNullMock).invoke()
        verifyZeroInteractions(notNullMock)
    }

    @Test
    fun executeIfNotNullOrEmptyDetectsEmptyAndExecutesCorrectly()
    {
        val ifNullMock = mock<() -> Unit>()
        val notNullMock = mock<(String) -> Unit>()
        val string = ""

        string.executeIfNotNullOrEmpty(ifNullMock, notNullMock)

        verify(ifNullMock).invoke()
        verifyZeroInteractions(notNullMock)
    }

    @Test
    fun executeIfNotNullOrEmptyDetectsNotNullOrEmptyAndExecutesCorrectly()
    {
        val ifNullMock = mock<() -> Unit>()
        val notNullMock = mock<(String) -> Unit>()
        val string = "test me"

        string.executeIfNotNullOrEmpty(ifNullMock, notNullMock)

        verify(notNullMock).invoke("test me")
        verifyZeroInteractions(ifNullMock)
    }
}