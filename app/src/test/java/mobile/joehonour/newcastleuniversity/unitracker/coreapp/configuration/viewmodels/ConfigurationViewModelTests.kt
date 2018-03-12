package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationYearWeightingModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ConfigurationViewModelTests
{
    @Rule @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun canGetConfigurationSuccess()
    {
        val weightings = listOf(
                ConfigurationYearWeighting(1, 20),
                ConfigurationYearWeighting(2, 40),
                ConfigurationYearWeighting(3, 60))

        val configuration = Configuration(
                "Leeds",
                2014,
                4,
                75,
                240,
                weightings)

        val query = mock<IQueryUserState> {
            on { getUserConfiguration(any(), any()) } doAnswer {
                val onSuccess = it.arguments[1] as ((Configuration) -> Unit)
                onSuccess(configuration)
            }
        }

        val viewModel = ConfigurationViewModel(query)

        val yearWeightingAssert = UnorderedListAssert<Int, ConfigurationYearWeightingModel>({ it.year })
        yearWeightingAssert.asserts[1] =
                ConfigurationYearWeightingModelAssert(FieldAssert(1), FieldAssert(20))
        yearWeightingAssert.asserts[2] =
                ConfigurationYearWeightingModelAssert(FieldAssert(2), FieldAssert(40))
        yearWeightingAssert.asserts[3] =
                ConfigurationYearWeightingModelAssert(FieldAssert(3), FieldAssert(60))

        val configurationAssert = ConfigurationModelAssert(
                FieldAssert("Leeds"),
                FieldAssert(2014),
                FieldAssert(4),
                FieldAssert(75),
                FieldAssert(240),
                yearWeightingAssert)

        configurationAssert.doAssert(viewModel.configuration.value!!)
    }

    @Test
    fun canGetConfigurationFailure()
    {
        val query = mock<IQueryUserState> {
            on { getUserConfiguration(any(), any()) } doAnswer {
                val onError = it.arguments[0] as ((String?) -> Unit)
                onError("error getting configuration")
            }
        }

        val viewModel = ConfigurationViewModel(query)
        assertNull(viewModel.configuration.value)
    }
}