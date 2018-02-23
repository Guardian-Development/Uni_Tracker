package mobile.joehonour.newcastleuniversity.unitracker.di

import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.FirebaseAuthenticationProvider
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.*
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

private val viewModelsModule : Module = applicationContext {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}

private val authenticationModule = applicationContext {
    provide { FirebaseAuthenticationProvider() as IProvideAuthentication }
}

fun getDependencies() : List<Module> {
    return listOf(authenticationModule, viewModelsModule)
}