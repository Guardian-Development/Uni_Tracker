package mobile.joehonour.newcastleuniversity.unitracker.domain.authentication

import android.util.Log
import com.google.firebase.auth.*
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull

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