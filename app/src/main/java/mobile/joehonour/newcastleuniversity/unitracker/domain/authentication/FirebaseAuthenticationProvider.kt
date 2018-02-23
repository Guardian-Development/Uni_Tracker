package mobile.joehonour.newcastleuniversity.unitracker.domain.authentication

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.TwitterAuthProvider
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull

class FirebaseAuthenticationProvider : IProvideAuthentication {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override val userLoggedIn: Boolean
        get() = firebaseAuth.currentUser.notNull()

    override fun authenticateWithTwitterSession(
            userToken: String,
            userSecret: String,
            callback: (status: Boolean, errorMessage: String?) -> Unit) {

        val credential = TwitterAuthProvider.getCredential(userToken, userSecret)

        firebaseAuth
                .signInWithCredential(credential)
                .addOnCompleteListener({ it.run() })
                {
                    when(it.isSuccessful) {
                        true -> Log.i("auth", firebaseAuth.currentUser?.displayName)
                        false -> Log.e("auth", it.exception?.message)
                    }

                    callback(it.isSuccessful, it.exception?.message)
                }
    }

}