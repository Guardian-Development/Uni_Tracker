package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange

data class ConfigurationYearWeightingModel(
        val year: Int,
        val weighting: Int
)

class ConfigurationYearWeightingModelValidator(
        val weightingMinimum: Int = 0,
        val weightingMaximum:Int = 100
){
    fun validate(weighting: Int?) : Boolean =
            weighting.notNullWithinInclusiveRange(
                weightingMinimum, weightingMaximum)
}