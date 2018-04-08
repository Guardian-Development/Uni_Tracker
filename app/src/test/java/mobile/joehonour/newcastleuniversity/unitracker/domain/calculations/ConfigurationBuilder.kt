package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting

class ConfigurationBuilder
{
    private val universityName: String = "default name"
    private val yearStarted: Int = 1
    private val courseLength: Int = 4
    var targetPercentage: Int = 70
    var totalCredits: Int = 0
    private val yearWeightings: MutableList<ConfigurationYearWeighting> = mutableListOf()

    fun withYearWeighting(builder: ConfigurationYearWeightingBuilder.() -> Unit) : ConfigurationBuilder
    {
        val yearWeightingBuilder = ConfigurationYearWeightingBuilder()
        yearWeightingBuilder.builder()
        yearWeightings.add(yearWeightingBuilder.build())
        return this
    }

    fun build() : Configuration
    {
        return Configuration(universityName,
                yearStarted,
                courseLength,
                targetPercentage,
                totalCredits,
                yearWeightings)
    }
}

