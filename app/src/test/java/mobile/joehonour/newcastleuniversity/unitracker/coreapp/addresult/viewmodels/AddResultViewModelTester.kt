package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.viewmodels

import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.AddResultModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert

class AddResultViewModelTester(private val viewModel: AddResultViewModel)
{
    fun performActions(action: AddResultViewModel.() -> Unit) : AddResultViewModelTester
    {
        action.invoke(viewModel)
        return this
    }

    fun assertAvailableModulesSet(expectedModules: List<Module>)
    {
        val assert = UnorderedListAssert<String, ModuleModel>({ it.moduleCode })
        expectedModules.forEach({
            assert.asserts[it.moduleCode] = ModuleAssert(
                    FieldAssert(it.moduleCode),
                    FieldAssert(it.moduleName),
                    FieldAssert(it.moduleCredits),
                    FieldAssert(it.moduleYearStudied))
        })
        assert.doAssert(viewModel.availableModules.value!!)
    }

    fun assertAvailableModulesNull()
    {
        assert(viewModel.availableModules.value == null)
    }

    fun assertValidDataTrue() = Assert.assertTrue(viewModel.validDataEntered)
    fun assertValidDataFalse() = Assert.assertFalse(viewModel.validDataEntered)

    companion object
    {
        fun addResultViewModelTester(userState: IQueryUserState = mock(),
                                     dataStorage: IProvideDataStorage = mock(),
                                     authProvider: IProvideAuthentication = mock(),
                                     validator: AddResultModelValidator = mock()): AddResultViewModelTester
                = AddResultViewModelTester(AddResultViewModel(userState, dataStorage, authProvider, validator))
    }
}