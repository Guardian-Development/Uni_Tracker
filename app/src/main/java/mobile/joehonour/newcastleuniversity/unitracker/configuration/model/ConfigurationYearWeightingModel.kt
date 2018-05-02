package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange

/**
 * Provides a model for a year within a users configured university course.
 *
 * @param year the year within the degree this model represents.
 * @param weighting the weighting this year has within the students degree course.
 * @param creditsCompletedWithinYear the amount of credits a user completed in this year.
 */
data class ConfigurationYearWeightingModel(
        val year: Int,
        val weighting: Int,
        val creditsCompletedWithinYear: Int
)

/**
 * Provides functionality to validate whether a year weighting data model is valid before it has
 * a chance to become persisted to the domain.
 *
 * @param weightingMinimum the minimum weighting a year can have.
 * @param weightingMaximum the maximum weighting a year can have.
 */
class ConfigurationYearWeightingModelValidator(
        val weightingMinimum: Int = 0,
        val weightingMaximum:Int = 100
){
    fun validate(weighting: Int?, creditsCompletedWithinYear: Int?) : Boolean =
            weighting.notNullWithinInclusiveRange(weightingMinimum, weightingMaximum) &&
            creditsCompletedWithinYear.notNull()
}