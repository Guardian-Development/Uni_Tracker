package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration

/**
 * Provides a model for the users configuration, specifically for displaying within the configuration
 * section of the core application.
 *
 * @param universityName the university the student attends.
 * @param yearStarted the year the student started university.
 * @param courseLength the length of the course the student is studying.
 * @param targetPercentage the target percentage the student wants to achieve in their degree.
 * @param totalCredits the total credits the user has to complete in order to finish their course.
 * @param yearWeightings the configured year weightings of each year within the users course.
 */
data class ConfigurationModel(
        val universityName: String,
        val yearStarted: Int,
        val courseLength: Int,
        val targetPercentage: Int,
        val totalCredits: Int,
        val yearWeightings: List<ConfigurationYearWeightingModel>
){
    /**
     * Responsible for allowing the configuration model to be created from the corresponding
     * domain entity.
     */
    companion object
    {
        /**
         * Responsible for instantiating a configuration model from the domain model.
         */
        fun instanceFromConfiguration(configuration: Configuration) : ConfigurationModel
        {
            val yearWeightings = configuration.yearWeightings
                    .map(ConfigurationYearWeightingModel.Companion::instanceFromConfigurationYearWeighting)
            return ConfigurationModel(
                    configuration.universityName,
                    configuration.yearStarted,
                    configuration.courseLength,
                    configuration.targetPercentage,
                    configuration.totalCredits,
                    yearWeightings)
        }
    }
}
