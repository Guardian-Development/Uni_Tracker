package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModel
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert

class InitialSetupDataModelAssert(
        var universityName: FieldAssert<String>? = null,
        var yearStarted: FieldAssert<Int>? = null,
        var courseLength: FieldAssert<Int>? = null,
        var targetPercentage: FieldAssert<Int>? = null,
        var totalCredits: FieldAssert<Int>? = null
) : Assert<InitialSetupDataModel> {

    override fun doAssert(actualValue: InitialSetupDataModel) {
        universityName?.doAssert(actualValue.universityName)
        yearStarted?.doAssert(actualValue.yearStarted)
        courseLength?.doAssert(actualValue.courseLength)
        targetPercentage?.doAssert(actualValue.targetPercentage)
        totalCredits?.doAssert(actualValue.totalCredits)
    }
}