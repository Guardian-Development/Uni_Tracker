package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

class ConfigurationViewModel(private val userState: IQueryUserState,
                             private val authProvider: IProvideAuthentication) : ViewModel()
{
    val configuration: LiveData<ConfigurationModel> by lazy {
        val data = MutableLiveData<ConfigurationModel>()
        userState.getUserConfiguration(
                { Log.e("ConfigurationViewModel", "could not get user state")},
                { data.postValue(ConfigurationModel.instanceFromConfiguration(it))})
        data
    }

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
