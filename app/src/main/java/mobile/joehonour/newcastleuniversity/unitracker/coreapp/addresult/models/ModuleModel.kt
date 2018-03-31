package mobile.joehonour.newcastleuniversity.unitracker.coreapp.addresult.models

data class ModuleModel(val moduleCode: String,
                       val moduleName: String,
                       val moduleCredits: Int,
                       val moduleYearStudied: Int)
{
    override fun toString(): String = moduleCode + ": " + moduleName
}

