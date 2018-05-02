package mobile.joehonour.newcastleuniversity.unitracker.domain.storage.support

/**
 * Provides helper functions that can be used to build the keys to query specific elements of the
 * database.
 */
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
