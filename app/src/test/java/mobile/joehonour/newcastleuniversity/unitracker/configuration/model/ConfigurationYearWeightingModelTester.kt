package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import org.junit.Assert

class ConfigurationYearWeightingModelTester(
        var year: Int,
        var weighting: Int?,
        var creditsCompletedWithinYear: Int?
){
    fun withEdits(tester: ConfigurationYearWeightingModelTester.() -> Unit) : ConfigurationYearWeightingModelTester
    {
        tester.invoke(this)
        return this
    }

    fun validateConformsTo(validator: ConfigurationYearWeightingModelValidator)
    {
        Assert.assertTrue(validate(validator))
    }

    fun validateDoesNotConformTo(validator: ConfigurationYearWeightingModelValidator)
    {
        Assert.assertFalse(validate(validator))
    }

    private fun validate(validator: ConfigurationYearWeightingModelValidator) : Boolean =
            validator.validate(year, weighting, creditsCompletedWithinYear)

    companion object
    {
        fun configurationYearWeightingModelTester(validator: ConfigurationYearWeightingModelValidator)
                : ConfigurationYearWeightingModelTester =
                ConfigurationYearWeightingModelTester(
                        year = 1,
                        weighting = validator.weightingMinimum + 1,
                        creditsCompletedWithinYear = 0)
    }
}