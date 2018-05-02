package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models

/**
 * Provides a model for a configured module within the students university course, specifically for
 * displaying within the result section of the application.
 */
data class ModuleModel(val moduleCode: String,
                       val moduleName: String,
                       val moduleCredits: Int,
                       val moduleYearStudied: Int)
{
    /**
     * Provides a clean string representation of the model, so when it is displayed in a list it is
     * accurately represented.
     */
    override fun toString(): String = moduleCode + ": " + moduleName
}

