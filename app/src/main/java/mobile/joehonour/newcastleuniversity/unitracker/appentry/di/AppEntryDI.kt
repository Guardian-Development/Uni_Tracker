package mobile.joehonour.newcastleuniversity.unitracker.appentry.di

import mobile.joehonour.newcastleuniversity.unitracker.appentry.viewmodels.AppEntryViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object AppEntryDI
{
    val appEntryModule : Module = applicationContext {
        viewModel { AppEntryViewModel(get(), get()) }
    }
}
