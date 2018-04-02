package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty

class ModuleModelValidator
{
    fun validate(moduleCode: String?,
                 moduleName: String?,
                 moduleCredits: Int?,
                 moduleYearStudied: Int?) : Boolean =
            moduleCode.notNullOrEmpty() &&
            moduleName.notNullOrEmpty() &&
            moduleCredits.notNull() &&
            moduleYearStudied.notNull()
}