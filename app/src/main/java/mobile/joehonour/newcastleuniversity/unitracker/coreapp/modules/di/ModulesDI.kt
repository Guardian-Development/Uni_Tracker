package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels.AddModuleViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object ModulesDI
{
    val modulesModule: Module = applicationContext {
        viewModel { AddModuleViewModel(get(), ModuleModelValidator(), get(), get()) }
    }
}