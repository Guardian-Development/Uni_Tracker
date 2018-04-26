package mobile.joehonour.newcastleuniversity.unitracker.login.di

import mobile.joehonour.newcastleuniversity.unitracker.login.viewmodels.LoginViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object LoginDI
{
    val loginModule: Module = applicationContext {
        viewModel { LoginViewModel(get(), get()) }
    }
}