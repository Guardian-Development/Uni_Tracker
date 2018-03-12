package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ConfigurationYearWeighting
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert
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
}