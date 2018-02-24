package mobile.joehonour.newcastleuniversity.unitracker

import android.app.Application
import com.google.firebase.FirebaseApp
import com.twitter.sdk.android.core.Twitter
import mobile.joehonour.newcastleuniversity.unitracker.di.getDependencies
import org.koin.standalone.StandAloneContext.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        Twitter.initialize(this)

        startKoin(getDependencies())
    }
}