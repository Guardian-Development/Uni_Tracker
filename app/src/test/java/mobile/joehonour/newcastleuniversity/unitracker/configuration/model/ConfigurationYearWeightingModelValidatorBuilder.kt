package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

class ConfigurationYearWeightingModelValidatorBuilder(
        var weightingMinimum: Int = 0,
        var weightingMaximum:Int = 100
) {
    fun withEdits(builder: ConfigurationYearWeightingModelValidatorBuilder.() -> Unit)
            : ConfigurationYearWeightingModelValidatorBuilder
    {
        builder.invoke(this)
        return this
    }

    fun build() : ConfigurationYearWeightingModelValidator =
            ConfigurationYearWeightingModelValidator(
                    weightingMinimum,
                    weightingMaximum)

    companion object
    {
        fun configurationYearWeightingModelValidatorBuilder() : ConfigurationYearWeightingModelValidatorBuilder =
                ConfigurationYearWeightingModelValidatorBuilder()
    }
}