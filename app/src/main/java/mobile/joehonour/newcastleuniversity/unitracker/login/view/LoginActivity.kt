package mobile.joehonour.newcastleuniversity.unitracker.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_login.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.configuration.view.ConfigurationActivity
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.CoreAppTabContainerActivity
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.LoginViewModel
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.handleFacebookSession
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.handleGoogleSession
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.handleTwitterSession
import org.koin.android.architecture.ext.viewModel

/**
 * The login screen provides the user the ability to login to the application using a variety of
 * social media accounts.
 */
class LoginActivity : AppCompatActivity()
{
    private val viewModel: LoginViewModel by viewModel()
    private val facebookCallbackManager: CallbackManager = CallbackManager.Factory.create()

    private val googleSignInCode = 10

    /**
     * Responsible for binding the social media login buttons to their respective actions.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerTwitterLoginButton()
        registerFacebookLoginButton()
        registerGoogleLoginButton()
    }

    /**
     * Registers the action executed when the Twitter login button is clicked.
     */
    private fun registerTwitterLoginButton()
    {
        twitterLoginButton.callback = viewModel.handleTwitterSession(
                { Log.e("LoginViewModel", it) },
                this::redirectFromSuccessfulLogin)
    }

    /**
     * Registers the action executed when the Facebook login button is clicked.
     */
    private fun registerFacebookLoginButton()
    {
        if (AccessToken.getCurrentAccessToken() != null)
        {
            LoginManager.getInstance().logOut()
        }

        facebookLoginButton.setReadPermissions("email", "public_profile")
        facebookLoginButton.registerCallback(facebookCallbackManager, viewModel.handleFacebookSession(
                { Log.e("LoginViewModel", it) },
                this::redirectFromSuccessfulLogin))
    }

    /**
     * Registers the action executed when the Google login button is clicked.
     */
    private fun registerGoogleLoginButton()
    {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build()

        val signInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        googleLoginButton.setOnClickListener {
            val signInIntent = signInClient.signInIntent
            startActivityForResult(signInIntent, googleSignInCode)
        }
    }

    /**
     * Redirects the user into the application on successful login.
     * This directs the user to the core app if they completed the app setup before, else it directs
     * them to the app setup page of the application.
     */
    private fun redirectFromSuccessfulLogin()
    {
        viewModel.userHasCompletedSetup {
            when(it) {
                true -> startActivity(Intent(this, CoreAppTabContainerActivity::class.java))
                false -> startActivity(Intent(this, ConfigurationActivity::class.java))
            }
        }
        finish()
    }

    /**
     * Provides the handling of each login event when it has occurred, notifying the login providers
     * of the result.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == googleSignInCode)
        {
            viewModel.handleGoogleSession(
                    {Log.e("LoginViewModel", it)}, GoogleSignIn.getSignedInAccountFromIntent(data)) {
                redirectFromSuccessfulLogin()
            }
        }

        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
