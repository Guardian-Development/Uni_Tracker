package mobile.joehonour.newcastleuniversity.unitracker.domain.models

data class AppConfigurationYearWeighting(
        val year: Int,
        val weighting: Int
){
    //used by Firebase
    constructor() : this(0, 0)
}

data class AppConfiguration(
        val universityName: String,
        val yearStarted: Int,
        val courseLength: Int,
        val targetPercentage: Int,
        val totalCredits: Int,
        val yearWeightings: List<AppConfigurationYearWeighting>
){
    //used by Firebase
    constructor() : this("", 0, 0, 0, 0, emptyList())
}
