package mobile.joehonour.newcastleuniversity.unitracker

import android.app.Application
import mobile.joehonour.newcastleuniversity.unitracker.di.getDependencies
import org.koin.standalone.StandAloneContext.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(getDependencies())
    }
}