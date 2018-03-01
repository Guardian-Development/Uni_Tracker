package mobile.joehonour.newcastleuniversity.unitracker.model

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange

data class InitialSetupYearWeightingModel(
        val year: Int,
        val weighting: Int
)

class InitialSetupYearWeightingModelValidator(
        val weightingMinimum: Int = 0,
        val weightingMaximum:Int = 100
){
    fun validate(weighting: Int?) : Boolean =
            weighting.notNullWithinInclusiveRange(
                weightingMinimum, weightingMaximum)
}