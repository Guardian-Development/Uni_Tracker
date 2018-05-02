package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange

/**
 * Provides functionality to validate whether a result is valid before it has a chance to become
 * persisted to the domain.
 */
class AddResultModelValidator
{
    /**
     * Provides validation for the parameters passed, making sure they fall within their respective
     * ranges and are not null.
     *
     * @param resultName the name of the result.
     * @param resultWeightingPercentage the weight of the result within its respective module.
     * @param resultPercentage the percentage achieved within this result.
     *
     * @return true if all values are not null and inclusive of their valid ranges, else false.
     */
    fun validate(resultName: String?, resultWeightingPercentage: Int?, resultPercentage: Double?) : Boolean
    {
        return resultName.notNullOrEmpty() &&
                resultWeightingPercentage.notNullWithinInclusiveRange(0, 100) &&
                resultPercentage.notNullWithinInclusiveRange(0.0, 100.0)
    }
}