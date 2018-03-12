package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration

data class ConfigurationModel(
        val universityName: String,
        val yearStarted: Int,
        val courseLength: Int,
        val targetPercentage: Int,
        val totalCredits: Int,
        val yearWeightings: List<ConfigurationYearWeightingModel>
){
    companion object {
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
