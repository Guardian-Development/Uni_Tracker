package mobile.joehonour.newcastleuniversity.unitracker.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.SplashViewModel
import org.koin.android.architecture.ext.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(viewModel.userLoggedIn) {
            true -> startActivity(Intent(this, MainActivity::class.java))
            false -> startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }
}