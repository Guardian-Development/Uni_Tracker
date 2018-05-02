package mobile.joehonour.newcastleuniversity.unitracker.coreapp.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.di.AddResultDI
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.di.CoreAppConfigurationDI
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.dashboard.di.DashboardDI
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.di.ModulesDI
import org.koin.dsl.module.Module

/**
 * Responsible for providing specific dependencies for all functionality relating to the Core App
 * section of the application. This is done by asking each specific section for its dependencies.
 */
object CoreAppDI
{
    val configurationModule : List<Module> = listOf(
            CoreAppConfigurationDI.configurationModule,
            ModulesDI.modulesModule,
            AddResultDI.addResultModule,
            DashboardDI.dashboardModule)
}
