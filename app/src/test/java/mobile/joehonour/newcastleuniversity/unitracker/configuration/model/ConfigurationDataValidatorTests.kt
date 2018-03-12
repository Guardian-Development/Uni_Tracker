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
                    courseLengthMinValue = 1
                    courseLengthMaxValue = 5
                    targetPercentageMinValue = 40
                    targetPercentageMaxValue = 80
                    totalCreditsMinValue = 10
                    totalCreditsMaxValue = 100 }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    universityName = "A valid university"
                    yearStarted = 2001
                    courseLength = 4
                    targetPercentage = 79
                    totalCredits = 50
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
    fun validateDataModelBelowCourseLengthMinValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    courseLengthMinValue = 3
                    courseLengthMaxValue = 10
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    courseLength = 2
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }

    @Test
    fun validateDataModelAboveCourseLengthMaxValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    courseLengthMinValue = 80
                    courseLengthMaxValue = 100
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    courseLength = 101
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

    @Test
    fun validateDataModelBelowTotalCreditsMinValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    totalCreditsMinValue = 50
                    totalCreditsMaxValue = 400
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    totalCredits = 49
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }

    @Test
    fun validateDataModelAboveTotalCreditsMaxValueDetected()
    {
        val configurationDataModelValidator = configurationDataModelValidatorBuilder()
                .withEdits {
                    totalCreditsMinValue = 50
                    totalCreditsMaxValue = 400
                }
                .build()

        configurationDataModelTester(configurationDataModelValidator)
                .withEdits {
                    totalCredits = 401
                }
                .validateDoesNotConformTo(configurationDataModelValidator)
    }
}