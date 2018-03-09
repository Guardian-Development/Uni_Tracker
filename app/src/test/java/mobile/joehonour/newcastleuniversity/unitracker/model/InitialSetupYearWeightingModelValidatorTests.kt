package mobile.joehonour.newcastleuniversity.unitracker.model

import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModelTester.Companion.initialYearWeightingModelTester
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModelValidatorBuilder.Companion.initialSetupYearWeightingModelValidatorBuilder
import org.junit.Test

class InitialSetupYearWeightingModelValidatorTests
{
    @Test
    fun validateYearWeightingModelValidDataModelDetected()
    {
        val initialYearWeightingValidator = initialSetupYearWeightingModelValidatorBuilder()
                .withEdits {
                    weightingMinimum = 0
                    weightingMaximum = 100
                }
                .build()

        initialYearWeightingModelTester(initialYearWeightingValidator)
                .withEdits {
                    year = 1
                    weighting = 75
                }
                .validateConformsTo(initialYearWeightingValidator)
    }

    @Test
    fun validateYearWeightingModelBelowYearWeightingMinValueDetected()
    {
        val initialYearWeightingValidator = initialSetupYearWeightingModelValidatorBuilder()
                .withEdits {
                    weightingMinimum = 30
                    weightingMaximum = 75
                }
                .build()

        initialYearWeightingModelTester(initialYearWeightingValidator)
                .withEdits {
                    year = 3
                    weighting = 29
                }
                .validateDoesNotConformTo(initialYearWeightingValidator)
    }

    @Test
    fun validateYearWeightingModelAboveYearWeightingMinValueDetected()
    {
        val initialYearWeightingValidator = initialSetupYearWeightingModelValidatorBuilder()
                .withEdits {
                    weightingMinimum = 0
                    weightingMaximum = 100
                }
                .build()

        initialYearWeightingModelTester(initialYearWeightingValidator)
                .withEdits {
                    year = 2
                    weighting = 101
                }
                .validateDoesNotConformTo(initialYearWeightingValidator)
    }
}