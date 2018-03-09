package mobile.joehonour.newcastleuniversity.unitracker.support

class FieldAssert<in T>(private val expectedValue: T) : Assert<T>
{
    override fun doAssert(actualValue: T)
    {
        org.junit.Assert.assertEquals("field assert found different values", expectedValue, actualValue)
    }
}