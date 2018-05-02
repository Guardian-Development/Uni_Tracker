package mobile.joehonour.newcastleuniversity.unitracker.domain.models

/**
 * Represents the entire student record for a student using this application. This includes their
 * configuration along with the modules (and their respective results) that they have recorded.
 */
data class StudentRecord(val configuration: Configuration,
                         val modules: Map<String, Module>)
{
    //used by Firebase
    constructor() : this(Configuration(), emptyMap())
}