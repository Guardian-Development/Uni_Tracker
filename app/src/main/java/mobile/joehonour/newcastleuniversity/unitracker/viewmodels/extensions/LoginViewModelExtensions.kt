package mobile.joehonour.newcastleuniversity.unitracker.viewmodels.extensions

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.LoginViewModel

fun LoginViewModel.handleTwitterSession(
        failureCallback: ((String?) -> Unit)? = null,
        successCallback: () -> Unit) = object : Callback<TwitterSession>() {

    override fun success(result: Result<TwitterSession>) {
        authenticateWithTwitterSession(result.data) { status, message ->
            when (status) {
                true -> successCallback()
                false -> failureCallback?.invoke(message)
            }
        }
    }

    override fun failure(exception: TwitterException) {
        failureCallback?.invoke(exception.message)
    }
}
