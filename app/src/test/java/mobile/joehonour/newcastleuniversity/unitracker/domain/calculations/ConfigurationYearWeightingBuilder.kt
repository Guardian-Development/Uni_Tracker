package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting

class ConfigurationYearWeightingBuilder
{
    var year: Int = 2018
    var weighting: Int = 0
    var creditsCompletedWithinYear: Int = 0

    fun withProperties(builder: ConfigurationYearWeightingBuilder.() -> Unit)
            : ConfigurationYearWeightingBuilder
    {
        this.builder()
        return this
    }

    fun build() : ConfigurationYearWeighting
    {
        return ConfigurationYearWeighting(year, weighting, creditsCompletedWithinYear)
    }
}