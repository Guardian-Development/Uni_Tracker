package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import com.nhaarman.mockito_kotlin.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.AppConfiguration
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import org.junit.Test

import org.junit.Assert.*

class UserStateQueryTests {

    @Test
    fun canGetUserHasCompletedInitialSetupTrue() {
        val callback = mock<(Boolean) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }
        val dataAccessor = mock<IProvideDataAccess> {
            on { readItemFromDatabase(
                    eq("id" + "/configuration"),
                    eq(AppConfiguration::class.java),
                    any(),
                    any()) } doAnswer { callback.invoke(true) }
        }
        val query = UserStateQuery(dataAccessor, authProvider)

        query.userHasCompletedInitialSetup(callback)

        verify(callback).invoke(true)
    }

    @Test
    fun canGetUserHasCompletedInitialSetupFalse() {
        val callback = mock<(Boolean) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn true
            on { userUniqueId } doReturn "id"
        }
        val dataAccessor = mock<IProvideDataAccess> {
            on { readItemFromDatabase(
                    eq("id" + "/configuration"),
                    eq(AppConfiguration::class.java),
                    any(),
                    any()) } doAnswer { callback.invoke(false) }
        }
        val query = UserStateQuery(dataAccessor, authProvider)

        query.userHasCompletedInitialSetup(callback)

        verify(callback).invoke(false)
    }

    @Test
    fun userNotLoggedInCauseInitialSetupFalse() {
        val callback = mock<(Boolean) -> Unit>()
        val authProvider = mock<IProvideAuthentication> {
            on { userLoggedIn } doReturn false
            on { userUniqueId } doReturn "id"
        }
        val query = UserStateQuery(mock(), authProvider)

        query.userHasCompletedInitialSetup(callback)

        verify(callback).invoke(false)
    }
}