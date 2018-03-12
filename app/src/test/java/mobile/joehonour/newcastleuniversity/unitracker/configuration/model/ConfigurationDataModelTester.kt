package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import junit.framework.Assert

class ConfigurationDataModelTester(
        var universityName: String,
        var yearStarted: Int,
        var courseLength: Int,
        var targetPercentage: Int,
        var totalCredits: Int
){
    fun withEdits(tester: ConfigurationDataModelTester.() -> Unit) : ConfigurationDataModelTester
    {
        tester.invoke(this)
        return this
    }

    fun validateConformsTo(validator: ConfigurationDataModelValidator)
    {
        Assert.assertTrue(validate(validator))
    }

    fun validateDoesNotConformTo(validator: ConfigurationDataModelValidator)
    {
        Assert.assertFalse(validate(validator))
    }

    private fun validate(validator: ConfigurationDataModelValidator) : Boolean =
            validator.validate(
                    universityName,
                    yearStarted,
                    courseLength,
                    targetPercentage,
                    totalCredits)

    companion object
    {
        fun configurationDataModelTester(validator: ConfigurationDataModelValidator) : ConfigurationDataModelTester =
                ConfigurationDataModelTester(
                        "Valid Name",
                        validator.yearStartedMinValue + 1,
                        validator.courseLengthMinValue + 1,
                        validator.targetPercentageMinValue + 1,
                        validator.totalCreditsMinValue + 1)
    }
}