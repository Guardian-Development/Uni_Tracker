package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert

class ConfigurationYearWeightingAssert(
        private val year: FieldAssert<Int>,
        private val weighting: FieldAssert<Int>,
        private val creditsCompleted: FieldAssert<Int>) : Assert<ConfigurationYearWeighting>
{
    override fun doAssert(actualValue: ConfigurationYearWeighting)
    {
        year.doAssert(actualValue.year)
        weighting.doAssert(actualValue.weighting)
        creditsCompleted.doAssert(actualValue.creditsCompletedWithinYear)
    }
}