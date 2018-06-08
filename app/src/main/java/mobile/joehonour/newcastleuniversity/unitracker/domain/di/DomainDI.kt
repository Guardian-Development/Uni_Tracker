package mobile.joehonour.newcastleuniversity.unitracker.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.FirebaseAuthenticationProvider
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideModuleCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.IProvideStudentTargetCalculations
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.ModuleCalculator
import mobile.joehonour.newcastleuniversity.unitracker.domain.calculations.StudentTargetCalculator
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.UserStateQuery
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.*
import org.koin.dsl.module.applicationContext

object DomainDI
{
    val domainModule = applicationContext {
        provide { FirebaseAuthenticationProvider(FirebaseAuth.getInstance()) as IProvideAuthentication }
        provide { FirebaseDataStorage(FirebaseDatabase.getInstance().reference) as IProvideDataStorage }
        provide { FirebaseDataReadAccess(FirebaseDatabase.getInstance().reference) as IProvideDataSingleReadAccess }
        provide { FirebaseDataReadAccess(FirebaseDatabase.getInstance().reference) as IProvideDataConstantListeningReadAccess }
        provide { UserStateQuery(get(), get(), get()) as IQueryUserState }
        provide { ModuleCalculator() as IProvideModuleCalculations }
        provide { StudentTargetCalculator() as IProvideStudentTargetCalculations }
    }
}