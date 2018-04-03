package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import java.util.*

class ModuleResultBuilder
{
    private val resultId: String = UUID.randomUUID().toString()
    private val resultName: String = "default name"
    var resultWeighting: Int = 0
    var resultPercentage: Double = 0.0

    fun build() : ModuleResult
    {
        return ModuleResult(resultId, resultName, resultWeighting, resultPercentage)
    }
}