package mobile.joehonour.newcastleuniversity.unitracker.di

import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.TestViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

private val viewModulesModule : Module = applicationContext {
    viewModel { TestViewModel() }
}

fun getDependencies() : List<Module> {
    return listOf(viewModulesModule)
}