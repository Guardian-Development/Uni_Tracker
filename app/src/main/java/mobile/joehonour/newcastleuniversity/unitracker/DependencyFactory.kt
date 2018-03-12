package mobile.joehonour.newcastleuniversity.unitracker

import mobile.joehonour.newcastleuniversity.unitracker.configuration.di.ConfigurationDI
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.di.CoreAppDI
import mobile.joehonour.newcastleuniversity.unitracker.domain.di.DomainDI
import mobile.joehonour.newcastleuniversity.unitracker.login.di.LoginDI
import org.koin.dsl.module.Module

fun getDependencies() : List<Module> = listOf(
        DomainDI.domainModule,
        LoginDI.loginVModule,
        ConfigurationDI.configurationModule)
        .plus(CoreAppDI.configurationModule)