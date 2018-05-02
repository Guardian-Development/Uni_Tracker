package mobile.joehonour.newcastleuniversity.unitracker.appentry.di

import mobile.joehonour.newcastleuniversity.unitracker.appentry.viewmodels.AppEntryViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Responsible for providing specific dependencies for all functionality relating to the App Entry
 * section of the application.
 */
object AppEntryDI
{
    val appEntryModule : Module = applicationContext {
        viewModel { AppEntryViewModel(get(), get()) }
    }
}
