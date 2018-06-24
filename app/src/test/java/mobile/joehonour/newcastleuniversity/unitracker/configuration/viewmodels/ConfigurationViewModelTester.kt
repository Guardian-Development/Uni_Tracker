package mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelValidator

class ConfigurationViewModelTester(private val viewModel: ConfigurationViewModel)
{
    fun performActions(action: ConfigurationViewModel.() -> Unit) : ConfigurationViewModelTester
    {
        action.invoke(viewModel)
        return this
    }

    fun fillAllFieldsWithDefaultValues() : ConfigurationViewModelTester
            = performActions {
                universityName.value = "default"
                yearStarted.value = 0
                targetPercentage.value = 0 }

    fun buildAndAssertConfiguration(builder: ConfigurationDataModelAssert.() -> Unit)
    {
        val assert = ConfigurationDataModelAssert()
        builder.invoke(assert)
        assert.doAssert(viewModel.buildConfigurationData())
    }

    fun assertValidDataTrue() = assertTrue(viewModel.validDataEntered)
    fun assertValidDataFalse() = assertFalse(viewModel.validDataEntered)

    companion object
    {
        fun configurationViewModelTester(validator: ConfigurationDataModelValidator) : ConfigurationViewModelTester =
                ConfigurationViewModelTester(ConfigurationViewModel(validator))
    }
}