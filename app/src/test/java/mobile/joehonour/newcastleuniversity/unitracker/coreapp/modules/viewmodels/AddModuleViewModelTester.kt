package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage

class AddModuleViewModelTester(private val viewModel: AddModuleViewModel)
{
    fun performActions(action: AddModuleViewModel.() -> Unit) : AddModuleViewModelTester
    {
        action.invoke(viewModel)
        return this
    }

    fun assertValidDataTrue() = Assert.assertTrue(viewModel.validDataEntered)
    fun assertValidDataFalse() = Assert.assertFalse(viewModel.validDataEntered)

    companion object
    {
        fun addModuleViewModelTester(userState: IQueryUserState = mock(),
                                     validator: ModuleModelValidator = mock(),
                                     authProvider: IProvideAuthentication = mock(),
                                     dataStorage: IProvideDataStorage = mock()) : AddModuleViewModelTester =
                AddModuleViewModelTester(AddModuleViewModel(userState, validator, authProvider, dataStorage))
    }
}