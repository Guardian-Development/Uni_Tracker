package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelTester.Companion.configurationYearWeightingModelTester
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidatorBuilder.Companion.configurationYearWeightingModelValidatorBuilder
import org.junit.Test

class ConfigurationYearWeightingModelValidatorTests
{
    @Test
    fun validateYearWeightingModelValidDataModelDetected()
    {
        val configurationYearWeightingValidator = configurationYearWeightingModelValidatorBuilder()
                .withEdits {
                    weightingMinimum = 0
                    weightingMaximum = 100
                }
                .build()

        configurationYearWeightingModelTester(configurationYearWeightingValidator)
                .withEdits {
                    year = 1
                    weighting = 75
                }
                .validateConformsTo(configurationYearWeightingValidator)
    }

    @Test
    fun validateYearWeightingModelBelowYearWeightingMinValueDetected()
    {
        val configurationYearWeightingValidator = configurationYearWeightingModelValidatorBuilder()
                .withEdits {
                    weightingMinimum = 30
                    weightingMaximum = 75
                }
                .build()

        configurationYearWeightingModelTester(configurationYearWeightingValidator)
                .withEdits {
                    year = 3
                    weighting = 29
                }
                .validateDoesNotConformTo(configurationYearWeightingValidator)
    }

    @Test
    fun validateYearWeightingModelAboveYearWeightingMinValueDetected()
    {
        val configurationYearWeightingValidator = configurationYearWeightingModelValidatorBuilder()
                .withEdits {
                    weightingMinimum = 0
                    weightingMaximum = 100
                }
                .build()

        configurationYearWeightingModelTester(configurationYearWeightingValidator)
                .withEdits {
                    year = 2
                    weighting = 101
                }
                .validateDoesNotConformTo(configurationYearWeightingValidator)
    }
}