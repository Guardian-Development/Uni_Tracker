package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module

interface IQueryUserState
{
    fun userHasCompletedConfiguration(result: (Boolean) -> Unit)

    fun getUserConfiguration(onError: (String?) -> Unit, onSuccess: (Configuration) -> Unit)

    fun getUserModules(onError: (String?) -> Unit, onSuccess: (List<Module>) -> Unit)
}

