package mobile.joehonour.newcastleuniversity.unitracker

import android.app.Application
import com.google.firebase.FirebaseApp
import com.twitter.sdk.android.core.Twitter
import org.koin.standalone.StandAloneContext.startKoin

/**
 * Provides the base application.
 * This is responsible for initialising all frameworks required of the application.
 */
class BaseApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        Twitter.initialize(this)

        startKoin(getDependencies())
    }
}