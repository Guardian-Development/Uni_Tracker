package mobile.joehonour.newcastleuniversity.unitracker.coreapp.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.di.CoreAppConfigurationDI
import org.koin.dsl.module.Module

object CoreAppDI
{
    val configurationModule : List<Module> = listOf(CoreAppConfigurationDI.configurationModule)
}
