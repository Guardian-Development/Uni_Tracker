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

/**
 * The loading screen is responsible for displaying an intermediate screen while the app loads the
 * users configuration and can then work out what entry page they should be displayed.
 */
class LoadingScreenActivity : AppCompatActivity()
{
    private val viewModel: AppEntryViewModel by viewModel()

    /**
     * Responsible for deciding which page the user should be entered into the app on.
     *
     * If the user needs to login: presents login page.
     * If the user is logged in, but they haven't configured the app: presents configuration page.
     * If the user is logged in, and has configured the app: presents the core application.
     */
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
