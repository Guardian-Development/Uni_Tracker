package mobile.joehonour.newcastleuniversity.unitracker.login.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import mobile.joehonour.newcastleuniversity.unitracker.configuration.view.ConfigurationActivity
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.CoreAppTabContainerActivity
import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.LoginViewModel
import org.koin.android.architecture.ext.viewModel

class SplashActivity : AppCompatActivity()
{
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        when(viewModel.userLoggedIn) {
            true -> {
                viewModel.userHasCompletedSetup {
                    when(it) {
                        true -> startActivity(Intent(this, CoreAppTabContainerActivity::class.java))
                        false -> startActivity(Intent(this, ConfigurationActivity::class.java))
                    }
                    finish()
                }
            }
            false -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}