package mobile.joehonour.newcastleuniversity.unitracker.domain.calculations

import mobile.joehonour.newcastleuniversity.unitracker.domain.models.Module

class ModuleCalculationTester(builder: ModuleBuilder.() -> Unit)
{
    private val module: Module

    init {
        val moduleBuilder = ModuleBuilder()
        moduleBuilder.builder()
        module = moduleBuilder.build()
    }

    fun performActionOnModule(moduleProvider: (Module) -> Unit)
    {
        moduleProvider(module)
    }
}