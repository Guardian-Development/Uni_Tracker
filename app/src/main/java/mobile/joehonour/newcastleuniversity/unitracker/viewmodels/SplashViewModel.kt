package mobile.joehonour.newcastleuniversity.unitracker.viewmodels

import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication

class SplashViewModel(private val authProvider: IProvideAuthentication) : ViewModel() {

    val userLoggedIn: Boolean by lazy { authProvider.userLoggedIn }
}
