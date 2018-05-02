package mobile.joehonour.newcastleuniversity.unitracker.domain.models

/**
 * Represents a students configuration within the application.
 */
data class Configuration(
        val universityName: String,
        val yearStarted: Int,
        val courseLength: Int,
        val targetPercentage: Int,
        val totalCredits: Int,
        val yearWeightings: List<ConfigurationYearWeighting>
){
    //used by Firebase
    constructor() : this("", 0, 0, 0, 0, emptyList())
}
