package mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels

import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

fun LoginViewModel.handleTwitterSession(
        failureCallback: ((String?) -> Unit)? = null,
        successCallback: () -> Unit) = object : Callback<TwitterSession>()
{
    override fun success(result: Result<TwitterSession>)
    {
        authenticateWithTwitterSession(result.data) { status, message ->
            when (status) {
                true -> successCallback()
                false -> failureCallback?.invoke(message)
            }
        }
    }

    override fun failure(exception: TwitterException)
    {
        failureCallback?.invoke(exception.message)
    }
}

fun LoginViewModel.handleFacebookSession(
        failureCallback: ((String?) -> Unit)? = null,
        successCallback: () -> Unit) = object : FacebookCallback<LoginResult>
{
    override fun onError(error: FacebookException?)
    {
        failureCallback?.invoke(error?.message)
    }

    override fun onCancel()
    {
        failureCallback?.invoke("User cancelled request")
    }

    override fun onSuccess(result: LoginResult?)
    {
        when(result) {
            null -> failureCallback?.invoke("Login result was null")
            else ->  authenticateWithFacebookSession(result.accessToken) { status, message ->
                when (status) {
                    true -> successCallback()
                    false -> failureCallback?.invoke(message)
                }
            }
        }
    }
}

fun LoginViewModel.handleGoogleSession(
        failureCallback: ((String?) -> Unit)? = null,
        signInTask: Task<GoogleSignInAccount>,
        successCallback: () -> Unit)
{
    try
    {
        val account = signInTask.getResult(ApiException::class.java)

        when(account.idToken) {
            null -> failureCallback?.invoke("ID token was null")
            else -> {
                authenticateWithGoogleSession(account.idToken!!) { status, message ->
                    when (status) {
                        true -> successCallback()
                        false -> failureCallback?.invoke(message)
                    }
                }
            }
        }
    }
    catch (e: ApiException)
    {
        failureCallback?.invoke(e.message)
    }
}