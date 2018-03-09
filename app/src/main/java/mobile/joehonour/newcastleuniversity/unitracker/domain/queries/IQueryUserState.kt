package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

interface IQueryUserState {
    fun userHasCompletedInitialSetup(result: (Boolean) -> Unit)
}

