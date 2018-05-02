package mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.viewmodels.DashboardViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Responsible for providing specific dependencies for all functionality relating to the Dashboard
 * section of the application.
 */
object DashboardDI
{
    val dashboardModule: Module = applicationContext {
        viewModel { DashboardViewModel(get(), get(), get()) }
    }
}
