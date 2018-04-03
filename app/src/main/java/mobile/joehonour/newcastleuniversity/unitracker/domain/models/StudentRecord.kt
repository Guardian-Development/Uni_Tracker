package mobile.joehonour.newcastleuniversity.unitracker.domain.models

data class StudentRecord(val configuration: Configuration,
                         val modules: Map<String, Module>)
{
    //used by Firebase
    constructor() : this(Configuration(), emptyMap())
}