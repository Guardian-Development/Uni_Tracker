package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting

data class ConfigurationYearWeightingModel(
        val year: Int,
        val weighting: Int
){
    companion object
    {
        fun instanceFromConfigurationYearWeighting(
                yearWeighting: ConfigurationYearWeighting) : ConfigurationYearWeightingModel
        {
            return ConfigurationYearWeightingModel(yearWeighting.year, yearWeighting.weighting)
        }
    }
}