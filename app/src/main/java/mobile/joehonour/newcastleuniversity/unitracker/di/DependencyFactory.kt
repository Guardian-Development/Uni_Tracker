package mobile.joehonour.newcastleuniversity.unitracker.di

import com.google.firebase.database.FirebaseDatabase
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.FirebaseAuthenticationProvider
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.FirebaseDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupDataModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.model.InitialSetupYearWeightingModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.InitialSetupViewModel
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.InitialSetupYearWeightingViewModel
import mobile.joehonour.newcastleuniversity.unitracker.viewmodels.LoginViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

private val domainModule = applicationContext {
    provide { FirebaseAuthenticationProvider() as IProvideAuthentication }
    provide { FirebaseDataStorage(FirebaseDatabase.getInstance().reference) as IProvideDataStorage }
}

private val viewModelsModule : Module = applicationContext {
    provide { InitialSetupDataModelValidator() }
    provide { InitialSetupYearWeightingModelValidator() }
    viewModel { LoginViewModel(get()) }
    viewModel { InitialSetupViewModel(get()) }
    viewModel { InitialSetupYearWeightingViewModel(get(), get(), get()) }
}

fun getDependencies() : List<Module> {
    return listOf(domainModule, viewModelsModule)
}