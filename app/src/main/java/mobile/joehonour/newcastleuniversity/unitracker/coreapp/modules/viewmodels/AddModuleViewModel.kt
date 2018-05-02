package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModelValidator
import mobile.joehonour.newcastleuniversity.unitracker.domain.authentication.IProvideAuthentication
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.queries.IQueryUserState
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.IProvideDataStorage
import mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support.DataLocationKeys

/**
 * The view model is responsible for presenting information to the add module view.
 *
 * @param userState provides functionality to query over the users data.
 * @param moduleModelValidator provides functionality to validate details entered by a user for a module.
 * @param authProvider provides functionality to query over the authentication details of the user.
 * @param dataStorage provides functionality to store objects within the database.
 */
class AddModuleViewModel(private val userState: IQueryUserState,
                         private val moduleModelValidator: ModuleModelValidator,
                         private val authProvider: IProvideAuthentication,
                         private val dataStorage: IProvideDataStorage) : ViewModel()
{
    val moduleCode: MutableLiveData<String> = MutableLiveData()
    val moduleName: MutableLiveData<String> = MutableLiveData()
    val moduleCredits: MutableLiveData<Int> = MutableLiveData()
    val moduleYearStudied: MutableLiveData<Int> = MutableLiveData()

    /**
     * Responsible for querying whether the data entered by the user, stored within the view model
     * fields, is valid.
     */
    val validDataEntered : Boolean
        get() = moduleModelValidator.validate(
                moduleCode .value,
                moduleName.value,
                moduleCredits.value,
                moduleYearStudied.value)

    /**
     * Responsible for fetching the years available for a module to be registered against, based on
     * the users configuration.
     *
     * @param onError is executed if an error occurs when fetching the years studied.
     * @param onSuccess is executed on successful fetching of the years studied, being passed the
     * available years.
     */
    fun availableYearsStudied(onError: (String?) -> Unit, onSuccess: (List<Int>) -> Unit)
    {
        userState.getUserConfiguration(onError) {
            onSuccess(it.yearWeightings.map { it.year }.toList())
        }
    }

    /**
     * Responsible for saving a module, based on the data entered by the user,  if it is valid.
     *
     * @param onError is executed if an error occurs when the module is being saved.
     * @param onSuccess is executed when the module has been saved successfully.
     */
    fun saveModule(onError: (String?) -> Unit, onSuccess: () -> Unit)
    {
        if(validDataEntered)
        {
            val module = buildModuleFromViewModelFields()
            when(authProvider.userLoggedIn)
            {
                true -> dataStorage.addItemToDatabase(
                        DataLocationKeys.studentModuleLocation(
                                authProvider.userUniqueId!!,
                                module.moduleCode),
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

    /**
     * Responsible for building a module from the data entered by the user, saved within the view
     * model fields.
     */
    private fun buildModuleFromViewModelFields() : Module =
            Module(moduleCode.value!!,
                    moduleName.value!!,
                    moduleCredits.value!!,
                    moduleYearStudied.value!!,
                    emptyMap())
}