package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.di

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models.AddResultModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.viewmodels.AddResultViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

object AddResultDI
{
    val addResultModule: Module = applicationContext {
        viewModel { AddResultViewModel(get(), get(), get(), AddResultModelValidator()) }
    }
}
