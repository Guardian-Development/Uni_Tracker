package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels.ConfigurationViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Responsible for providing specific dependencies for all functionality relating to the
 * core app Configuration section of the application.
 */
object CoreAppConfigurationDI
{
    val configurationModule : Module = applicationContext {
        viewModel { ConfigurationViewModel(get(), get()) }
    }
}
