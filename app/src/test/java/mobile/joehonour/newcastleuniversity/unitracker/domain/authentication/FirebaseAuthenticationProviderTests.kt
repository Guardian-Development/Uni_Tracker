package mobile.joehonour.newcastleuniversity.unitracker.domain.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FirebaseAuthenticationProviderTests
{
    @Test
    fun canGetUserLoggedInTrue()
    {
        val firebaseUser = mock<FirebaseUser>()
        val firebaseAuth = mock<FirebaseAuth> {
            on { currentUser } doReturn firebaseUser
        }
        val authProvider = FirebaseAuthenticationProvider(firebaseAuth)

        assertTrue(authProvider.userLoggedIn)
    }

    @Test
    fun canGetUserLoggedInFalse()
    {
        val firebaseAuth = mock<FirebaseAuth> {
            on { currentUser }.doReturn<FirebaseUser?>(null)
        }
        val authProvider = FirebaseAuthenticationProvider(firebaseAuth)

        assertFalse(authProvider.userLoggedIn)
    }
}