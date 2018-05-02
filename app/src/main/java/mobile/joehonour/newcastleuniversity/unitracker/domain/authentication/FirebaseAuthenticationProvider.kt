package mobile.joehonour.newcastleuniversity.unitracker.domain.authentication

import android.util.Log
import com.google.firebase.auth.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull

/**
 * Responsible for authenticating a user with Google Firebase backend.
 *
 * @param firebaseAuth the Google Firebase service to authenticating with.
 */
class FirebaseAuthenticationProvider(private val firebaseAuth: FirebaseAuth) : IProvideAuthentication
{
    override val userUniqueId: String?
        get() = firebaseAuth.currentUser?.uid

    override val userLoggedIn: Boolean
        get() = firebaseAuth.currentUser.notNull()

    override fun authenticateWithTwitterSession(
            userToken: String,
            userSecret: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        val credential = TwitterAuthProvider.getCredential(userToken, userSecret)
        authenticateWithCredential(credential, callback)
    }

    override fun authenticateWithFacebookSession(
            accessToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        val credential = FacebookAuthProvider.getCredential(accessToken)
        authenticateWithCredential(credential, callback)
    }

    override fun authenticateWithGoogleSession(
            accessToken: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        val credential = GoogleAuthProvider.getCredential(accessToken, null)
        authenticateWithCredential(credential, callback)
    }

    /**
     * Responsible for making an authentication attempt to Google Firebase, given a Google Firebase
     * credential.
     *
     * @param credential the users credential to attempt to login with.
     * @param callback is executed with the result of the login attempt.
     */
    private fun authenticateWithCredential(
            credential: AuthCredential,
            callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        firebaseAuth
                .signInWithCredential(credential)
                .addOnCompleteListener({ it.run() })
                {
                    when (it.isSuccessful) {
                        true -> Log.i("auth", firebaseAuth.currentUser?.displayName)
                        false -> Log.e("auth", it.exception?.message)
                    }

                    callback(it.isSuccessful, it.exception?.message)
                }
    }

    override fun logout(callback: (status: Boolean, errorMessage: String?) -> Unit)
    {
        FirebaseAuth.getInstance().signOut()
        callback(true, null)
    }
}