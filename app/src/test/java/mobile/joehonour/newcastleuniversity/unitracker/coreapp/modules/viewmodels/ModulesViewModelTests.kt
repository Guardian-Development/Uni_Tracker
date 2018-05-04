package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ModulesViewModelTests
{
    // allows live data to work outside of an android environment
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun refreshCurrentModulesSuccessSetsPublicData()
    {
        val modules = listOf(
                Module("id1", "CSC3123", "module", 10, 2, emptyMap()),
                Module("id2", "CSC9876", "module2", 20, 1, emptyMap()))

        val userStateQuery = mock<IQueryUserState> {
            on { getUserModules(any(), any()) } doAnswer {
                val onSuccess = it.arguments[1] as ((List<Module>) -> Unit)
                onSuccess(modules)
            }
        }

        val viewModel = ModulesViewModel(userStateQuery)

        viewModel.refreshCurrentModules()

        val assert = UnorderedListAssert<String, ModuleModel>({it.moduleCode})
        assert.asserts["CSC3123"] = ModuleModelAssert(
                FieldAssert("id1"), FieldAssert("CSC3123"), FieldAssert("module"), FieldAssert(10), FieldAssert(2)
        )
        assert.asserts["CSC9876"] = ModuleModelAssert(
                FieldAssert("id2"), FieldAssert("CSC9876"), FieldAssert("module2"), FieldAssert(20), FieldAssert(1)
        )

        assert.doAssert(viewModel.currentModules.value!!)
    }

    @Test
    fun refreshCurrentModulesFailureDoesNotSetPublicData()
    {
        val userStateQuery = mock<IQueryUserState> {
            on { getUserModules(any(), any()) } doAnswer {
                val onError = it.arguments[0] as ((String?) -> Unit)
                onError("database error")
            }
        }

        val viewModel = ModulesViewModel(userStateQuery)
        viewModel.refreshCurrentModules()

        assert(viewModel.currentModules.value == null)
    }
}