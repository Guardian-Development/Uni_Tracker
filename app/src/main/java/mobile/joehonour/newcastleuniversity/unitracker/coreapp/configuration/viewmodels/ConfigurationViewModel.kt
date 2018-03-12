package mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.configuration.models.ConfigurationModel
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState

class ConfigurationViewModel(private val userState: IQueryUserState) : ViewModel()
{
    val configuration: LiveData<ConfigurationModel> by lazy {
        val data = MutableLiveData<ConfigurationModel>()
        userState.getUserConfiguration(
                { Log.e("ConfigurationViewModel", "could not get user state")},
                { data.postValue(ConfigurationModel.instanceFromConfiguration(it))})
        data
    }
}
