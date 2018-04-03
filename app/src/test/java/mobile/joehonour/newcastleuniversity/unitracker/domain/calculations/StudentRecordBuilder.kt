package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Configuration
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.StudentRecord

class StudentRecordBuilder
{
    private var configuration: Configuration = Configuration()
    private var modules: MutableList<Module> = mutableListOf()

    fun withConfiguration(builder: ConfigurationBuilder.() -> Unit) : StudentRecordBuilder
    {
        val configurationBuilder = ConfigurationBuilder()
        configurationBuilder.builder()
        configuration = configurationBuilder.build()
        return this
    }

    fun withModule(builder: ModuleBuilder.() -> Unit) : StudentRecordBuilder
    {
        val moduleBuilder = ModuleBuilder()
        moduleBuilder.builder()
        modules.add(moduleBuilder.build())
        return this
    }

    fun build() : StudentRecord
    {
        return StudentRecord(configuration, modules.map { it.moduleCode to it }.toMap())
    }
}