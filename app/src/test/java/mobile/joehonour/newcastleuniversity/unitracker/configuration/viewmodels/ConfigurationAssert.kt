package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert

class ConfigurationAssert(
        private val universityName: FieldAssert<String>,
        private val yearStarted: FieldAssert<Int>,
        private val courseLength: FieldAssert<Int>,
        private val targetPercentage: FieldAssert<Int>,
        private val totalCredits: FieldAssert<Int>,
        private val yearWeightings: UnorderedListAssert<Int, ConfigurationYearWeighting>) : Assert<Configuration>
{
    override fun doAssert(actualValue: Configuration)
    {
        universityName.doAssert(actualValue.universityName)
        yearStarted.doAssert(actualValue.yearStarted)
        courseLength.doAssert(actualValue.courseLength)
        targetPercentage.doAssert(actualValue.targetPercentage)
        totalCredits.doAssert(actualValue.totalCredits)
        yearWeightings.doAssert(actualValue.yearWeightings)
    }
}