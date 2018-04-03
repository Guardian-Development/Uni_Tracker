package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting

class ConfigurationBuilder
{
    private val universityName: String = "default name"
    private val yearStarted: Int = 1
    private val courseLength: Int = 4
    private val targetPercentage: Int = 70
    private val yearWeightings: List<ConfigurationYearWeighting> = emptyList()

    var totalCredits: Int = 0

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