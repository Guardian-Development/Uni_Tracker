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


class LoginActivity : AppCompatActivity()
{
    private val viewModel: LoginViewModel by viewModel()
    private val facebookCallbackManager: CallbackManager = CallbackManager.Factory.create()

    private val googleSignInCode = 10

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerTwitterLoginButton()
        registerFacebookLoginButton()
        registerGoogleLoginButton()
    }

    private fun registerTwitterLoginButton()
    {
        twitterLoginButton.callback = viewModel.handleTwitterSession(
                { Log.e("LoginViewModel", it) },
                this::redirectFromSuccessfulLogin)
    }

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
