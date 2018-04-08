package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module
import mobile.joehonour.newcastleuniversity.unitracker.domain.models.ModuleResult
import java.util.*

class ModuleBuilder
{
    var moduleCredits: Int = 0
    var moduleYearSudied: Int = 0

    private val results: MutableList<ModuleResult> = mutableListOf()

    fun withProperties(builder: ModuleBuilder.() -> Unit) : ModuleBuilder
    {
        this.builder()
        return this
    }

    fun withResult(builder: ModuleResultBuilder.() -> Unit) : ModuleBuilder
    {
        val resultBuilder = ModuleResultBuilder()
        resultBuilder.builder()
        results.add(resultBuilder.build())
        return this
    }

    fun build() : Module
    {
        return Module(UUID.randomUUID().toString(),
                "default name",
                moduleCredits,
                moduleYearSudied,
                results.map { it.resultId to it }.toMap())
    }
}