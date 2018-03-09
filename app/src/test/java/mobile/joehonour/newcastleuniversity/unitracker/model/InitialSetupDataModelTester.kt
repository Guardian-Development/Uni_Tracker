package mobile.joehonour.newcastleuniversity.unitracker.model

import junit.framework.Assert

class InitialSetupDataModelTester(
        var universityName: String,
        var yearStarted: Int,
        var courseLength: Int,
        var targetPercentage: Int,
        var totalCredits: Int
){
    fun withEdits(tester: InitialSetupDataModelTester.() -> Unit) : InitialSetupDataModelTester
    {
        tester.invoke(this)
        return this
    }

    fun validateConformsTo(validator: InitialSetupDataModelValidator)
    {
        Assert.assertTrue(validate(validator))
    }

    fun validateDoesNotConformTo(validator: InitialSetupDataModelValidator)
    {
        Assert.assertFalse(validate(validator))
    }

    private fun validate(validator: InitialSetupDataModelValidator) : Boolean =
            validator.validate(
                    universityName,
                    yearStarted,
                    courseLength,
                    targetPercentage,
                    totalCredits)

    companion object
    {
        fun initialDataModelTester(validator: InitialSetupDataModelValidator) : InitialSetupDataModelTester =
                InitialSetupDataModelTester(
                        "Valid Name",
                        validator.yearStartedMinValue + 1,
                        validator.courseLengthMinValue + 1,
                        validator.targetPercentageMinValue + 1,
                        validator.totalCreditsMinValue + 1)
    }
}