package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration

interface IQueryUserState
{
    fun userHasCompletedConfiguration(result: (Boolean) -> Unit)

    fun getUserConfiguration(onError: (String?) -> Unit, onSuccess: (Configuration) -> Unit)
}

