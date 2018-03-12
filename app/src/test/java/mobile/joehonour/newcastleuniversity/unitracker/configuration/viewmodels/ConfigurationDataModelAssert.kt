package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModel
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert

class ConfigurationDataModelAssert(
        var universityName: FieldAssert<String>? = null,
        var yearStarted: FieldAssert<Int>? = null,
        var courseLength: FieldAssert<Int>? = null,
        var targetPercentage: FieldAssert<Int>? = null,
        var totalCredits: FieldAssert<Int>? = null
) : Assert<ConfigurationDataModel>
{
    override fun doAssert(actualValue: ConfigurationDataModel)
    {
        universityName?.doAssert(actualValue.universityName)
        yearStarted?.doAssert(actualValue.yearStarted)
        courseLength?.doAssert(actualValue.courseLength)
        targetPercentage?.doAssert(actualValue.targetPercentage)
        totalCredits?.doAssert(actualValue.totalCredits)
    }
}