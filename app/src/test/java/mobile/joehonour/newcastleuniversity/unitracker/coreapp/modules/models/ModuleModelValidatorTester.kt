package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import junit.framework.Assert

class ModuleModelValidatorTester(
        var moduleCode: String?,
        var moduleName: String?,
        var moduleCredits: Int?,
        var moduleYearStudied: Int?
){
    fun withEdits(tester: ModuleModelValidatorTester.() -> Unit) : ModuleModelValidatorTester
    {
        tester.invoke(this)
        return this
    }

    fun validateConformsTo(validator: ModuleModelValidator)
    {
        Assert.assertTrue(validate(validator))
    }

    fun validateDoesNotConformTo(validator: ModuleModelValidator)
    {
        Assert.assertFalse(validate(validator))
    }

    private fun validate(validator: ModuleModelValidator) : Boolean =
            validator.validate(moduleCode, moduleName, moduleCredits, moduleYearStudied)

    companion object
    {
        fun moduleModelValidatorTester() : ModuleModelValidatorTester =
                ModuleModelValidatorTester("validcode", "validname", 10, 1)
    }
}