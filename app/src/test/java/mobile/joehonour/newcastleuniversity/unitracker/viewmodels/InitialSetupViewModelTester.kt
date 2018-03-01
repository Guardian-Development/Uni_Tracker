package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModelValidator

class InitialSetupViewModelTester(private val viewModel: InitialSetupViewModel) {

    fun performActions(action: InitialSetupViewModel.() -> Unit) : InitialSetupViewModelTester {
        action.invoke(viewModel)
        return this
    }

    fun fillAllFieldsWithDefaultValues() : InitialSetupViewModelTester
            = performActions {
                universityName.value = "default"
                yearStarted.value = 0
                courseLength.value = 0
                targetPercentage.value = 0
                totalCredits.value = 0 }

    fun buildAndAssertInitialSetupData(builder: InitialSetupDataModelAssert.() -> Unit) {
        val assert = InitialSetupDataModelAssert()
        builder.invoke(assert)
        assert.doAssert(viewModel.buildInitialSetupData())
    }

    fun assertValidDataTrue() = assertTrue(viewModel.validDataEntered)
    fun assertValidDataFalse() = assertFalse(viewModel.validDataEntered)

    companion object {
        fun initialSetupViewModelTester(validator: InitialSetupDataModelValidator) : InitialSetupViewModelTester =
                InitialSetupViewModelTester(InitialSetupViewModel(validator))
    }
}