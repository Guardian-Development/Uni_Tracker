package mobile.joehonour.newcastleuniversity.unitracker.coreapp.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.di.CoreAppConfigurationDI
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.di.ModulesDI
import org.koin.dsl.module.Module

object CoreAppDI
{
    val configurationModule : List<Module> = listOf(
            CoreAppConfigurationDI.configurationModule,
            ModulesDI.modulesModule)
}
