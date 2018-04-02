package mobile.joehonour.newcastleuniversity.unitracker.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.FirebaseAuthenticationProvider
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.ModuleCalculator
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.UserStateQuery
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.FirebaseDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.FirebaseDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataAccess
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import org.koin.dsl.module.applicationContext

object DomainDI
{
    val domainModule = applicationContext {
        provide { FirebaseAuthenticationProvider(FirebaseAuth.getInstance()) as IProvideAuthentication }
        provide { FirebaseDataStorage(FirebaseDatabase.getInstance().reference) as IProvideDataStorage }
        provide { FirebaseDataAccess(FirebaseDatabase.getInstance().reference) as IProvideDataAccess }
        provide { UserStateQuery(get(), get()) as IQueryUserState }
        provide { ModuleCalculator() as IProvideModuleCalculations }
    }
}