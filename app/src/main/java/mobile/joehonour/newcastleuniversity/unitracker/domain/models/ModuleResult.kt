package mobile.joehonour.newcastleuniversity.unitracker.domain.models

/**
 * Represents an individual recorded result for a module within the application.
 */
data class ModuleResult(val resultId: String,
                        val resultName: String,
                        val resultWeighting: Int,
                        val resultPercentage: Double)
{
    //used by Firebase
    constructor() : this("","", 0, 0.0)
}