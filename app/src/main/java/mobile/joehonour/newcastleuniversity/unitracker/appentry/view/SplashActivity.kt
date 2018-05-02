package mobile.joehonour.newcastleuniversity.unitracker.appentry.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * The splash activity is responsible for displaying a screen as the app loads its resources into
 * memory etc.
 */
class SplashActivity : AppCompatActivity()
{
    /**
     * Once the app has loaded all resources it starts the loading screen activity which controls
     * the app entry location for the user.
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoadingScreenActivity::class.java))
        finish()
    }
}