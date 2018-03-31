package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models

import org.junit.Assert

class AddResultModelValidatorTester(
        var addResultName: String?,
        var addResultWeightingPercentage: Int?,
        var addResultPercentage: Double?)
{
    fun withEdits(tester: AddResultModelValidatorTester.() -> Unit) : AddResultModelValidatorTester
    {
        tester.invoke(this)
        return this
    }

    fun validateConformsTo(validator: AddResultModelValidator)
    {
        Assert.assertTrue(validate(validator))
    }

    fun validateDoesNotConformTo(validator: AddResultModelValidator)
    {
        Assert.assertFalse(validate(validator))
    }

    private fun validate(validator: AddResultModelValidator) : Boolean =
            validator.validate(addResultName, addResultWeightingPercentage, addResultPercentage)

    companion object
    {
        fun addResultModelValidatorTester() : AddResultModelValidatorTester
        {
            return AddResultModelValidatorTester("valid name", 50, 50.0)
        }
    }
}