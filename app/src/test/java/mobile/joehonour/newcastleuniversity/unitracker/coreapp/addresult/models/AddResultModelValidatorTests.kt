package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.AddResultModelValidatorTester.Companion.addResultModelValidatorTester
import org.junit.Test

class AddResultModelValidatorTests
{
    @Test
    fun validateDataModelAllFieldsValidSuccess()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultName = "Valid Name"
                    addResultWeightingPercentage = 75
                    addResultPercentage = 90.0
                }
                .validateConformsTo(validator)
    }

    @Test
    fun validateDataModelAddResultNameEmptyFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultName = ""
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultNameNullFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultName = null
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultWeightingNullFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultWeightingPercentage = null
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultWeightingAtMinimumSuccess()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultWeightingPercentage = 0
                }
                .validateConformsTo(validator)
    }

    @Test
    fun validateDataModelAddResultWeightingBelowMinimumFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultWeightingPercentage = -1
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultWeightAtMaximumSuccess()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultWeightingPercentage = 100
                }
                .validateConformsTo(validator)
    }

    @Test
    fun validateDataModelAddResultWeightAboveMaximumFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultWeightingPercentage = 101
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultPercentageNullFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultPercentage = null
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultPercentageAtMinimumSucces()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultPercentage = 0.0
                }
                .validateConformsTo(validator)
    }

    @Test
    fun validateDataModelAddResultPercentageBelowMinimumFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultPercentage = -0.01
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelAddResultPercentageAtMaximumSuccess()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultPercentage = 100.0
                }
                .validateConformsTo(validator)
    }

    @Test
    fun validateDataModelAddResultPercentageAboveMaximumFailure()
    {
        val validator = AddResultModelValidator()
        addResultModelValidatorTester()
                .withEdits {
                    addResultPercentage = 100.01
                }
                .validateDoesNotConformTo(validator)
    }
}