package mobile.joehonour.newcastleuniversity.unitracker.configuration.di

import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationDataModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.configuration.model.ConfigurationYearWeightingModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationViewModel
import mobile.joehonour.newcastleuniversity.unitracker.configuration.viewmodels.ConfigurationYearWeightingViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Responsible for providing specific dependencies for all functionality relating to the
 * Configuration section of the application.
 */
object ConfigurationDI
{
    val configurationModule : Module = applicationContext {
        provide { ConfigurationDataModelValidator() }
        provide { ConfigurationYearWeightingModelValidator() }
        viewModel { ConfigurationViewModel(get()) }
        viewModel { ConfigurationYearWeightingViewModel(get(), get(), get()) }
    }
}
