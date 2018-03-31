package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange

class AddResultModelValidator
{
    fun validate(resultName: String?, resultWeightingPercentage: Int?, resultPercentage: Double?) : Boolean
    {
        return resultName.notNullOrEmpty() &&
                resultWeightingPercentage.notNullWithinInclusiveRange(0, 100) &&
                resultPercentage.notNullWithinInclusiveRange(0.0, 100.0)
    }
}