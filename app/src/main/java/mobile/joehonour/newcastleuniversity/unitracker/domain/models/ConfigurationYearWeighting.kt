package mobile.joehonour.newcastleuniversity.unitracker.domain.models

/**
 * Represents a students configuration of an individual year within their degree.
 */
data class ConfigurationYearWeighting(
        val year: Int,
        val weighting: Int,
        val creditsCompletedWithinYear: Int
){
    //used by Firebase
    constructor() : this(0, 0, 0)
}