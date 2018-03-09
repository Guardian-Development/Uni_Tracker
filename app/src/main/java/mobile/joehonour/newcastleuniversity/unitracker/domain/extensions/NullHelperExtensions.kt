package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

fun <T> T.notNull(): Boolean
{
    return this != null
}

fun String?.notNullOrEmpty() : Boolean
{
    return this != null && this != ""
}

fun Int?.notNullWithinInclusiveRange(start: Int, end: Int): Boolean
{
    return when(this.notNull()) {
        true -> this!! in start..end
        false -> false
    }
}

fun String?.executeIfNotNullOrEmpty(ifNull: () -> Unit, notNull: (String) -> Unit)
{
    when(this.notNullOrEmpty()) {
        true -> notNull(this!!)
        false -> ifNull()
    }
}