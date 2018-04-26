package mobile.joehonour.newcastleuniversity.unitracker.appentry.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LoadingScreenActivity::class.java))
        finish()
    }
}