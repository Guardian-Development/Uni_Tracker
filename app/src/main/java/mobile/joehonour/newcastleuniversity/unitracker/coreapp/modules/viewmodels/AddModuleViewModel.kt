package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage

class AddModuleViewModel(private val userState: IQueryUserState,
                         private val moduleModelValidator: ModuleModelValidator,
                         private val authProvider: IProvideAuthentication,
                         private val dataStorage: IProvideDataStorage) : ViewModel()
{
    val moduleCode: MutableLiveData<String> = MutableLiveData()
    val moduleName: MutableLiveData<String> = MutableLiveData()
    val moduleCredits: MutableLiveData<Int> = MutableLiveData()
    val moduleYearStudied: MutableLiveData<Int> = MutableLiveData()

    val validDataEntered : Boolean
        get() = moduleModelValidator.validate(
                moduleCode .value,
                moduleName.value,
                moduleCredits.value,
                moduleYearStudied.value)

    fun availableYearsStudied(onError: (String?) -> Unit, onSuccess: (List<Int>) -> Unit)
    {
        userState.getUserConfiguration(onError) {
            onSuccess(it.yearWeightings.map { it.year }.toList())
        }
    }

    fun saveModule(onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        if(validDataEntered)
        {
            val module = buildModuleFromViewModelFields()
            when(authProvider.userLoggedIn)
            {
                true -> dataStorage.addItemToDatabase(
                        authProvider.userUniqueId!! + "/modules/" + module.moduleCode,
                        module,
                        onError,
                        onSuccess)
                false -> onError("User not logged in")
            }
        }
        else
        {
            onError("Invalid data entered")
        }
    }

    private fun buildModuleFromViewModelFields() : Module =
            Module(moduleCode.value!!,
                    moduleName.value!!,
                    moduleCredits.value!!,
                    moduleYearStudied.value!!,
                    emptyMap())
}