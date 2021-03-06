package mobile.joehonour.newcastleuniversity.unitracker

import mobile.joehonour.newcastleuniversity.unitracker.appentry.di.AppEntryDI
import mobile.joehonour.newcastleuniversity.unitracker.configuration.di.ConfigurationDI
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.di.CoreAppDI
import mobile.joehonour.newcastleuniversity.unitracker.domain.di.DomainDI
import mobile.joehonour.newcastleuniversity.unitracker.login.di.LoginDI
import org.koin.dsl.module.Module

fun getDependencies() : List<Module> = listOf(
        DomainDI.domainModule,
        AppEntryDI.appEntryModule,
        LoginDI.loginModule,
        ConfigurationDI.configurationModule)
        .plus(CoreAppDI.configurationModule)