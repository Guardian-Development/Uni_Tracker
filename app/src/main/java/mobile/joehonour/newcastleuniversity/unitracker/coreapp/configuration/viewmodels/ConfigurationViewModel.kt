package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

/**
 * The view model is responsible for presenting information to the core app configuration view.
 *
 * @param userState provides functionality to query the users configuration.
 * @param authProvider provides functionality to log the user out of the system.
 */
class ConfigurationViewModel(private val userState: IQueryUserState,
                             private val authProvider: IProvideAuthentication) : ViewModel()
{
    /**
     * Responsible for holding the users current configuration, which is fetched from the database.
     */
    val configuration: LiveData<ConfigurationModel> by lazy {
        val data = MutableLiveData<ConfigurationModel>()
        userState.getUserConfiguration(
                { Log.e("ConfigurationViewModel", "could not get user state")},
                { data.postValue(ConfigurationModel.instanceFromConfiguration(it))})
        data
    }

    /**
     * Responsible for logging out of the application.
     *
     * @param onError is executed if the user could not be logged out for any reason.
     * @param onSuccess is executed when the user is successfully logged out of the application.
     */
    fun logoutOfApplication(onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        authProvider.logout { status, errorMessage ->
            when(status) {
                true -> onSuccess()
                false -> onError.invoke(errorMessage)
            }
        }
    }
}
