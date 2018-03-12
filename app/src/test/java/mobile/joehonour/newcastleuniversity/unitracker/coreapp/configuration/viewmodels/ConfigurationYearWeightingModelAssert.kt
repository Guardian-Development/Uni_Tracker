package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert

class ConfigurationYearWeightingModelAssert(
        private val year: FieldAssert<Int>,
        private val weighting: FieldAssert<Int>) : Assert<ConfigurationYearWeightingModel>
{
    override fun doAssert(actualValue: ConfigurationYearWeightingModel) {
        year.doAssert(actualValue.year)
        weighting.doAssert(actualValue.weighting)
    }
}