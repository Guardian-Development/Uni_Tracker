package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidatorTester.Companion.moduleModelValidatorTester
import org.junit.Test

class ModuleModelValidatorTests
{
    @Test
    fun validateDataModelAllFieldsValidSuccess()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleCode = "CSC1234"
                    moduleName = "Valid Module"
                    moduleCredits = 30
                    moduleYearStudied = 2
                }
                .validateConformsTo(validator)
    }

    @Test
    fun validateDataModelModuleCodeEmptyFailure()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleCode = ""
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelModuleCodeNullFailure()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleCode = null
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelModuleNameEmptyFailure()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleName = ""
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelModuleNameNullFailure()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleName = null
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelModuleCreditsNullFailure()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleCredits = null
                }
                .validateDoesNotConformTo(validator)
    }

    @Test
    fun validateDataModelModuleYearStudiedNullFailure()
    {
        val validator = ModuleModelValidator()
        moduleModelValidatorTester()
                .withEdits {
                    moduleYearStudied = null
                }
                .validateDoesNotConformTo(validator)
    }
}