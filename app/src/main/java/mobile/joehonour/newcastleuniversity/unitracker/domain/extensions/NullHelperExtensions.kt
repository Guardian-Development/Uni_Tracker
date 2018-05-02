package mobile.joehonour.newcastleuniversity.unitracker.domain.extensions

/**
 * Responsible for calculating whether a generic object is null or not.
 *
 * @return true if object is not null, else false.
 */
fun <T> T.notNull(): Boolean
{
    return this != null
}

/**
 * Responsible for calculating whether a string is null or empty.
 *
 * @return true if the string is not null or empty, else false.
 */
fun String?.notNullOrEmpty() : Boolean
{
    return this != null && this != ""
}

/**
 * Responsible for calculating whether an int is inclusively within a given range and not null.
 *
 * @param start the inclusive start of the range.
 * @param end the inclusive end of the range.
 *
 * @return true if the Int is within the range, else false.
 */
fun Int?.notNullWithinInclusiveRange(start: Int, end: Int): Boolean
{
    return when(this.notNull()) {
        true -> this!! in start..end
        false -> false
    }
}

/**
 * Responsible for calculating whether a double is inclusively within a given range and not null.
 *
 * @param start the inclusive start of the range.
 * @param end the inclusive end of the range.
 *
 * @return true if the double is within the range, else false.
 */
fun Double?.notNullWithinInclusiveRange(start: Double, end: Double): Boolean
{
    return when(this.notNull()) {
        true -> this!! in start..end
        false -> false
    }
}

/**
 * Responsible for executing the ifNull function if an object is null, else it executes the not null
 * function passing in the string.
 *
 * @param ifNull executed when the string is null.
 * @param notNull executed when the string is not null, being passed the string.
 */
fun String?.executeIfNotNullOrEmpty(ifNull: () -> Unit, notNull: (String) -> Unit)
{
    when(this.notNullOrEmpty()) {
        true -> notNull(this!!)
        false -> ifNull()
    }
}