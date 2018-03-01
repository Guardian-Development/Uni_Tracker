package mobile.joehonour.newcastleuniversity.unitracker.model

import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModelTester.Companion.initialDataModelTester
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModelValidatorBuilder.Companion.initialSetupDataModelValidatorBuilder
import org.junit.Test

class InitialSetupDataValidatorTests {

    @Test
    fun validateDataModelValidDataModelDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
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

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    universityName = "A valid university"
                    yearStarted = 2001
                    courseLength = 4
                    targetPercentage = 79
                    totalCredits = 50
                }
                .validateConformsTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelBelowYearStartedMinValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    yearStartedMinValue = 2000
                    yearStartedMaxValue = 2005
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    yearStarted = 1999
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelAboveYearStartedMaxValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    yearStartedMinValue = 1970
                    yearStartedMaxValue = 2055
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    yearStarted = 2056
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelBelowCourseLengthMinValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    courseLengthMinValue = 3
                    courseLengthMaxValue = 10
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    courseLength = 2
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelAboveCourseLengthMaxValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    courseLengthMinValue = 80
                    courseLengthMaxValue = 100
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    courseLength = 101
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelBelowTargetPercentageMinValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    targetPercentageMinValue = 10
                    targetPercentageMaxValue = 100
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    targetPercentage = 9
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelAboveTargetPercentageMaxValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    targetPercentageMinValue = 10
                    targetPercentageMaxValue = 100
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    targetPercentage = 101
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelBelowTotalCreditsMinValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    totalCreditsMinValue = 50
                    totalCreditsMaxValue = 400
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    totalCredits = 49
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }

    @Test
    fun validateDataModelAboveTotalCreditsMaxValueDetected() {
        val initialDataModelValidator = initialSetupDataModelValidatorBuilder()
                .withEdits {
                    totalCreditsMinValue = 50
                    totalCreditsMaxValue = 400
                }
                .build()

        initialDataModelTester(initialDataModelValidator)
                .withEdits {
                    totalCredits = 401
                }
                .validateDoesNotConformTo(initialDataModelValidator)
    }
}