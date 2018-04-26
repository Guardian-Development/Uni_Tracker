package mobile.joehonour.newcastleuniversity.unitracker.appentry.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mobile.joehonour.newcastleuniversity.unitracker.R
import mobile.joehonour.newcastleuniversity.unitracker.appentry.viewmodels.AppEntryViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.view.ConfigurationActivity
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.CoreAppTabContainerActivity
import mobile.joehonour.newcastleuniversity.unitracker.login.view.LoginActivity
import org.koin.android.architecture.ext.viewModel

class LoadingScreenActivity : AppCompatActivity()
{
    private val viewModel: AppEntryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

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
