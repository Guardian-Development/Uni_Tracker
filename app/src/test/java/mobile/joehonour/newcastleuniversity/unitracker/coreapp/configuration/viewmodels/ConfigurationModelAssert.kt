package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert

class ConfigurationModelAssert(
        private val universityName: FieldAssert<String>,
        private val yearStarted: FieldAssert<Int>,
        private val courseLength: FieldAssert<Int>,
        private val targetPercentage: FieldAssert<Int>,
        private val totalCredits: FieldAssert<Int>,
        private val yearWeightings: UnorderedListAssert<Int, ConfigurationYearWeightingModel>) : Assert<ConfigurationModel>
{
    override fun doAssert(actualValue: ConfigurationModel)
    {
        universityName.doAssert(actualValue.universityName)
        yearStarted.doAssert(actualValue.yearStarted)
        courseLength.doAssert(actualValue.courseLength)
        targetPercentage.doAssert(actualValue.targetPercentage)
        totalCredits.doAssert(actualValue.totalCredits)
        yearWeightings.doAssert(actualValue.yearWeightings)
    }
}