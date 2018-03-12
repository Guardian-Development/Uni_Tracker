package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels.ConfigurationViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object CoreAppConfigurationDI
{
    val configurationModule : Module = applicationContext {
        viewModel { ConfigurationViewModel(get()) }
    }
}
