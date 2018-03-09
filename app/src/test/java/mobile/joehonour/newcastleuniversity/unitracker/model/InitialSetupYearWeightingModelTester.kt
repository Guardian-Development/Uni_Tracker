package mobile.joehonour.newcastleuniversity.unitracker.model

import org.junit.Assert

class InitialSetupYearWeightingModelTester(
        var year: Int,
        var weighting: Int
){
    fun withEdits(tester: InitialSetupYearWeightingModelTester.() -> Unit) : InitialSetupYearWeightingModelTester
    {
        tester.invoke(this)
        return this
    }

    fun validateConformsTo(validator: InitialSetupYearWeightingModelValidator)
    {
        Assert.assertTrue(validate(validator))
    }

    fun validateDoesNotConformTo(validator: InitialSetupYearWeightingModelValidator)
    {
        Assert.assertFalse(validate(validator))
    }

    private fun validate(validator: InitialSetupYearWeightingModelValidator) : Boolean =
            validator.validate(weighting)

    companion object
    {
        fun initialYearWeightingModelTester(validator: InitialSetupYearWeightingModelValidator)
                : InitialSetupYearWeightingModelTester =
                InitialSetupYearWeightingModelTester(
                        year = 1,
                        weighting = validator.weightingMinimum + 1)
    }
}