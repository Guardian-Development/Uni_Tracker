package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module

/**
 * Provides querying over the student record of the user currently logged in to the application.
 */
interface IQueryUserState
{
    /**
     * Responsible for determining if a student has completed and stored an app configuration.
     *
     * @param result is executed with the result of the query, passed true if the student has
     * configured the application, else false.
     */
    fun userHasCompletedConfiguration(result: (Boolean) -> Unit)

    /**
     * Responsible for getting the user configuration.
     *
     * @param onError executed if an error occurs when attempting to get the users configuration.
     * @param onSuccess executed when the users configuration was successfully found.
     */
    fun getUserConfiguration(onError: (String?) -> Unit, onSuccess: (Configuration) -> Unit)

    /**
     * Responsible for getting the list of all modules a user has configured within the application.
     *
     * @param onError executed if an error occurs when attempting to get the users configuration.
     * @param onSuccess executed when the list of modules was successfully found.
     */
    fun getUserModules(onError: (String?) -> Unit, onSuccess: (List<Module>) -> Unit)
}

