package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

object DataLocationKeys
{
    fun studentRecordLocation(studentId: String) : String = "$studentId/"

    fun studentConfigurationLocation(studentId: String) : String
            = "${studentRecordLocation(studentId)}configuration/"

    fun studentModulesLocation(studentId: String) : String
            = "${studentRecordLocation(studentId)}modules/"

    fun studentModuleLocation(studentId: String, moduleCode: String) : String
            = "${studentModulesLocation(studentId)}$moduleCode/"

    fun resultLocationForModule(studentId: String, moduleCode: String, resultId: String) : String
            = "${studentModuleLocation(studentId, moduleCode)}results/$resultId"
}
