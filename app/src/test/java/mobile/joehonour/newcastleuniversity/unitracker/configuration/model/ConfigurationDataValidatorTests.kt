package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelTester.Companion.configurationDataModelTester
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelValidatorBuilder.Companion.configurationDataModelValidatorBuilder
import org.junit.Test

class ConfigurationDataValidatorTests
{
    @Test
    fun validateDataModelValidDataModelDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    yearStartedMinValue = 2000
                    yearStartedMaxValue = 2018
                    targetPercentageMinValue = 40
                    targetPercentageMaxValue = 80 }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    universityName = "A valid university"
                    yearStarted = 2001
                    targetPercentage = 79
                }
                .validateConformsTo(configurationDataModelValidator)
    }

    @Test
    fun validateDataModelBelowYearStartedMinValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    yearStartedMinValue = 2000
                    yearStartedMaxValue = 2005
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    yearStarted = 1999
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }

    @Test
    fun validateDataModelAboveYearStartedMaxValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    yearStartedMinValue = 1970
                    yearStartedMaxValue = 2055
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    yearStarted = 2056
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }

    @Test
    fun validateDataModelBelowTargetPercentageMinValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    targetPercentageMinValue = 10
                    targetPercentageMaxValue = 100
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    targetPercentage = 9
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }

    @Test
    fun validateDataModelAboveTargetPercentageMaxValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    targetPercentageMinValue = 10
                    targetPercentageMaxValue = 100
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    targetPercentage = 101
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }
}