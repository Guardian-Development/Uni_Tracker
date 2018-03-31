package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
import mobile.joehonour.newcastleuniversity.unitracker.support.UnorderedListAssert
import org.junit.Test

class UserStateQueryTests
{
    @Test
    fun canGetUserHasCompletedConfigurationTrue()
    {
        val callback = mock<(Boolean) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }
        val dataAccessor = mock<IProvideDataAccess> {
            on { readItemFromDatabase(
                    eq("id" + "/configuration"),
                    eq(Configuration::class.java),
                    any(),
                    any()) } doAnswer { callback.invoke(true) }
        }
        val query = UserStateQuery(dataAccessor, authProvider)

        query.userHasCompletedConfiguration(callback)

        verify(callback).invoke(true)
    }

    @Test
    fun canGetUserHasCompletedConfigurationFalse()
    {
        val callback = mock<(Boolean) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }
        val dataAccessor = mock<IProvideDataAccess> {
            on { readItemFromDatabase(
                    eq("id" + "/configuration"),
                    eq(Configuration::class.java),
                    any(),
                    any()) } doAnswer { callback.invoke(false) }
        }
        val query = UserStateQuery(dataAccessor, authProvider)

        query.userHasCompletedConfiguration(callback)

        verify(callback).invoke(false)
    }

    @Test
    fun userNotLoggedInCauseConfigurationFalse()
    {
        val callback = mock<(Boolean) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
            on { userUniqueId } doReturn "id"
        }
        val query = UserStateQuery(mock(), authProvider)

        query.userHasCompletedConfiguration(callback)

        verify(callback).invoke(false)
    }

    @Test
    fun canGetUserConfigurationCompletedConfigurationTrue()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }

        val yearWeightings = listOf(
                ConfigurationYearWeighting(1, 10),
                ConfigurationYearWeighting(2, 20),
                ConfigurationYearWeighting(3, 40))

        val configuration = Configuration(
                "Newcastle",
                2014,
                4,
                75,
                240,
                yearWeightings)

        val onSuccess = mock<(Configuration) -> Unit>()

        val dataAccess = mock<IProvideDataAccess> {
            on { readItemFromDatabase<Configuration>(
                    eq("id/configuration"),
                    any(),
                    any(),
                    any()) } doAnswer { onSuccess(configuration) }
        }

        UserStateQuery(dataAccess, authProvider)
                .getUserConfiguration(mock(), onSuccess)

        verify(onSuccess).invoke(configuration)
    }

    @Test
    fun canGetUserConfigurationCompletedConfigurationFalse()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }

        val onError: (String?) -> Unit = {
            FieldAssert<String?>("No configuration saved").doAssert(it)
        }

        val dataAccess = mock<IProvideDataAccess> {
            on { readItemFromDatabase<Configuration>(
                    eq("id/configuration"),
                    any(),
                    any(),
                    any()) } doAnswer { onError("No configuration saved") }
        }

        UserStateQuery(dataAccess, authProvider)
                .getUserConfiguration(onError, mock())
    }

    @Test
    fun userNotLoggedInCauseGetConfigurationError()
    {
        val onError = mock<(String?) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
            on { userUniqueId } doReturn "id"
        }
        val query = UserStateQuery(mock(), authProvider)

        query.getUserConfiguration(onError, mock())

        verify(onError).invoke("user not logged in")
    }

    @Test
    fun canGetUserModulesUserLoggedInTrue()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }

        val modules = listOf(
                Module("CSC3123", "module", 10, 2),
                Module("CSC9876", "module2", 20, 1))

        val dataAccess = mock<IProvideDataAccess> {
            on { readCollectionFromDatabase<Module>(eq("id/modules"), any(), any(), any()) } doAnswer {
                val onSuccess = it.arguments[3] as ((List<Module>) -> Unit)
                onSuccess(modules)
            }
        }

        val query = UserStateQuery(dataAccess, authProvider)
        val onSuccess = { modules:List<Module> ->
            val assert = UnorderedListAssert<String, Module>({ it.moduleCode })
            assert.asserts["CSC3123"] = ModuleAssert(
                    FieldAssert("CSC3123"), FieldAssert("module"), FieldAssert(10), FieldAssert(2)
            )
            assert.asserts["CSC9876"] = ModuleAssert(
                    FieldAssert("CSC9876"), FieldAssert("module2"), FieldAssert(20), FieldAssert(1)
            )
            assert.doAssert(modules)
        }

        query.getUserModules(mock(), onSuccess)
    }

    @Test
    fun canGetUserModulesUserLoggedInError()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }

        val dataAccess = mock<IProvideDataAccess> {
            on { readCollectionFromDatabase<Module>(eq("id/modules"), any(), any(), any()) } doAnswer {
                val onError = it.arguments[2] as ((String?) -> Unit)
                onError("database failure")
            }
        }

        val query = UserStateQuery(dataAccess, authProvider)
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("database failure").doAssert(it.arguments[0] as (String))
            }
        }

        query.getUserModules(onError, mock())
        verify(onError).invoke(any())
    }

    @Test
    fun canNotGetUserModulesWhenUserNotLoggedIn()
    {
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
        }

        val query = UserStateQuery(mock(), authProvider)
        val onError = mock<((String?) -> Unit)> {
            on { invoke(any()) } doAnswer {
                FieldAssert("User not logged in").doAssert(it.arguments[0] as (String)) }
        }

        query.getUserModules(onError, mock())
        verify(onError).invoke(any())
    }
}

