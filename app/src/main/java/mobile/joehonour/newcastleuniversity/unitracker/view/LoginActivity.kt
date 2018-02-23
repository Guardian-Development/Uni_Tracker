package mobile.joehonour.newcastleuniversity.unitracker.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.activity_login.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        twitterLoginButton.callback = object : Callback<TwitterSession>() {

            override fun success(result: Result<TwitterSession>) {
                Log.i("test", "success")
                handleTwitterSession(result.data)
            }

            override fun failure(exception: TwitterException) {
                Log.i("test", "failure")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleTwitterSession(session: TwitterSession) {

        val credential = TwitterAuthProvider.getCredential(
                session.authToken.token,
                session.authToken.secret)

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, {
                    if (it.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        Log.i("auth", user?.displayName)
                    } else {
                        Log.i("auth", "failed auth")
                        Log.e("auth", it.exception.toString())
                    }
                })
    }
}
