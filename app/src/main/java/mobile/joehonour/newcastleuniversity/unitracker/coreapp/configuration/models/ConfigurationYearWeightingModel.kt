package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting

/**
 * Provides a model for a year within the users course, specifically for displaying within the
 * core app add result page.
 *
 * @param year the year in the course this represents.
 * @param weighting the weighing of this year within the students course.
 * @param creditsCompletedWithinYear the credits completed in this year within the students course.
 */
data class ConfigurationYearWeightingModel(
        val year: Int,
        val weighting: Int,
        val creditsCompletedWithinYear: Int
){
    /**
     * Responsible for creating the configuration year weighting model from the domain entity.
     */
    companion object
    {
        /**
         * Responsible for converting the domain entity to the year weighting model.
         */
        fun instanceFromConfigurationYearWeighting(
                yearWeighting: ConfigurationYearWeighting) : ConfigurationYearWeightingModel
        {
            return ConfigurationYearWeightingModel(
                    yearWeighting.year,
                    yearWeighting.weighting,
                    yearWeighting.creditsCompletedWithinYear)
        }
    }
}