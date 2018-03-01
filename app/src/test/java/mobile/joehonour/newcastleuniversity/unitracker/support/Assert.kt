package mobile.joehonour.newcastleuniversity.unitracker.support

interface Assert<in T> {
    fun doAssert(actualValue: T)
}