package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty

/**
 * Provides functionality to validate whether a module data model is valid before it has a chance to
 * become persisted to the domain.
 */
class ModuleModelValidator
{
    /**
     * Provides validation for the parameters passed, making sure they are not null.
     *
     * @param moduleCode the students currently entered module code.
     * @param moduleName the students currently entered module name.
     * @param moduleCredits the students currently entered module credits.
     * @param moduleYearStudied the students currently entered year studied.
     *
     * @return true if all values are not null, else false.
     */
    fun validate(moduleCode: String?,
                 moduleName: String?,
                 moduleCredits: Int?,
                 moduleYearStudied: Int?) : Boolean =
            moduleCode.notNullOrEmpty() &&
            moduleName.notNullOrEmpty() &&
            moduleCredits.notNull() &&
            moduleYearStudied.notNull()
}