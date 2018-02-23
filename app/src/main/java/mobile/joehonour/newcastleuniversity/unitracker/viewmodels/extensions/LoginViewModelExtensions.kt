package mobile.joehonour.newcastleuniversity.unitracker.viewmodels.extensions

import android.content.Intent
import android.util.Log
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.view.LoginActivity
import mobile.joehonour.newcastleuniversity.unitracker.view.MainActivity
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.LoginViewModel


fun LoginViewModel.handleTwitterSession(loginActivity: LoginActivity) = object : Callback<TwitterSession>() {

    override fun success(result: Result<TwitterSession>) {
        authenticateWithTwitterSession(result.data) { status, message ->
            when (status) {
                true -> {
                    loginActivity.startActivity(Intent(loginActivity, MainActivity::class.java))
                    loginActivity.finish()
                }
                false -> Log.e("LoginViewModel", message)
            }
        }
    }

    override fun failure(exception: TwitterException) {}
}
