package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

object DataLocationKeys
{
    fun studentRecordLocation(studentId: String) : String = "$studentId/"

    fun studentConfigurationLocation(studentId: String) : String
            = "${studentRecordLocation(studentId)}configuration/"

    fun studentModulesLocation(studentId: String) : String
            = "${studentRecordLocation(studentId)}modules/"

    fun studentModuleLocation(studentId: String, moduleId: String) : String
            = "${studentModulesLocation(studentId)}$moduleId/"

    fun resultLocationForModule(studentId: String, moduleId: String, resultId: String) : String
            = "${studentModuleLocation(studentId, moduleId)}results/$resultId"
}
