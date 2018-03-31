package mobile.joehonour.newcastleuniversity.unitracker.domain.queries

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.support.Assert
import mobile.joehonour.newcastleuniversity.unitracker.support.FieldAssert

class ModuleAssert(private val moduleCode: FieldAssert<String>,
                   private val moduleName: FieldAssert<String>,
                   private val moduleCredits: FieldAssert<Int>,
                   private val moduleYearStudied: FieldAssert<Int>) : Assert<Module>
{
    override fun doAssert(actualValue: Module)
    {
        moduleCode.doAssert(actualValue.moduleCode)
        moduleName.doAssert(actualValue.moduleName)
        moduleCredits.doAssert(actualValue.moduleCredits)
        moduleYearStudied.doAssert(actualValue.moduleYearStudied)
    }
}