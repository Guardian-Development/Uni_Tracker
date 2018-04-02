package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.AddModuleViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.IndividualModuleViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.ModulesViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object ModulesDI
{
    val modulesModule: Module = applicationContext {
        viewModel { AddModuleViewModel(get(), ModuleModelValidator(), get(), get()) }
        viewModel { ModulesViewModel(get()) }
        viewModel { IndividualModuleViewModel(get()) }
    }
}