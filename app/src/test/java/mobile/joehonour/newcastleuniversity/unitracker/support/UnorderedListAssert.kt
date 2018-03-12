package mobile.joehonour.newcastleuniversity.unitracker.support

class UnorderedListAssert<K, T>(private val keyForAssert: (T) -> K) : Assert<List<T>>
{
    val asserts = mutableMapOf<K, Assert<T>>()

    override fun doAssert(actualValue: List<T>)
    {
        actualValue.forEach{ assertFor(it).doAssert(it) }
        org.junit.Assert.assertEquals("Asserts size is not equal to actual size",
                asserts.size, actualValue.size)
    }

    private fun assertFor(itemToAssert: T) : Assert<T> =
            asserts.getOrElse(keyForAssert(itemToAssert),
                    { throw UnsupportedOperationException("No key for value")})
}