package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import java.time.LocalDateTime

class ConfigurationDataModelValidatorBuilder(
        var yearStartedMinValue: Int = 1950,
        var yearStartedMaxValue: Int = LocalDateTime.now().year,
        var targetPercentageMinValue: Int = 0,
        var targetPercentageMaxValue: Int = 100
) {

    fun withEdits(builder: ConfigurationDataModelValidatorBuilder.() -> Unit)
            : ConfigurationDataModelValidatorBuilder
    {
        builder.invoke(this)
        return this
    }

    fun build() : ConfigurationDataModelValidator =
            ConfigurationDataModelValidator(
                    yearStartedMinValue,
                    yearStartedMaxValue,
                    targetPercentageMinValue,
                    targetPercentageMaxValue)

    companion object
    {
        fun configurationDataModelValidatorBuilder() : ConfigurationDataModelValidatorBuilder =
                ConfigurationDataModelValidatorBuilder()
    }
}