package mobile.joehonour.newcastleuniversity.unitracker.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_login.*
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.LoginViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.view.ConfigurationActivity
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.CoreAppTabContainerActivity
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.handleFacebookSession
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.handleTwitterSession
import org.koin.android.architecture.ext.viewModel

class LoginActivity : AppCompatActivity()
{
    private val viewModel: LoginViewModel by viewModel()
    private val facebookCallbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        twitterLoginButton.callback = viewModel.handleTwitterSession(
                { Log.e("LoginViewModel", it) },
                this::redirectFromSuccessfulLogin)

        facebookLoginButton.setReadPermissions("email", "public_profile")
        facebookLoginButton.registerCallback(facebookCallbackManager, viewModel.handleFacebookSession(
                { Log.e("LoginViewModel", it) },
                this::redirectFromSuccessfulLogin))
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
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
