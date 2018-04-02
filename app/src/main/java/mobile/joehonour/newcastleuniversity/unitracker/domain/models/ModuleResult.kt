package mobile.joehonour.newcastleuniversity.unitracker.domain.models

data class ModuleResult(val resultId: String,
                        val resultName: String,
                        val resultWeighting: Int,
                        val resultPercentage: Double)
{
    //used by Firebase
    constructor() : this("","", 0, 0.0)
}