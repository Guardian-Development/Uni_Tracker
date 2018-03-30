package mobile.joehonour.newcastleuniversity.unitracker.domain.models

data class Module(val moduleCode: String,
                  val moduleName: String,
                  val moduleCredits: Int,
                  val moduleYearStudied: Int)
{
    //used by Firebase
    constructor() : this("", "", 0, 0)
}