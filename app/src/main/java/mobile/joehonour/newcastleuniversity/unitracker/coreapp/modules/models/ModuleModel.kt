package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty

data class ModuleModel(val moduleCode: String,
                       val moduleName: String,
                       val moduleCredits: Int,
                       val moduleYearStudied: Int)

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