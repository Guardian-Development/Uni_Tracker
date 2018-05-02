package mobile.joehonour.newcastleuniversity.unitracker.domain.models

/**
 * Represents a module configured by a student within the application.
 */
data class Module(val moduleCode: String,
                  val moduleName: String,
                  val moduleCredits: Int,
                  val moduleYearStudied: Int,
                  val results: Map<String, ModuleResult>)
{
    //used by Firebase
    constructor() : this("", "", 0, 0, emptyMap())
}