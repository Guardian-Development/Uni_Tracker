package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.viewmodels

import mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models.ModuleModel
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert

class ModuleModelAssert(private val moduleId: FieldAssert<String>,
                        private val moduleCode: FieldAssert<String>,
                        private val moduleName: FieldAssert<String>,
                        private val moduleCredits: FieldAssert<Int>,
                        private val moduleYearStudied: FieldAssert<Int>) : Assert<ModuleModel>
{
    override fun doAssert(actualValue: ModuleModel)
    {
        moduleId.doAssert(actualValue.moduleId)
        moduleCode.doAssert(actualValue.moduleCode)
        moduleName.doAssert(actualValue.moduleName)
        moduleCredits.doAssert(actualValue.moduleCredits)
        moduleYearStudied.doAssert(actualValue.moduleYearStudied)
    }
}