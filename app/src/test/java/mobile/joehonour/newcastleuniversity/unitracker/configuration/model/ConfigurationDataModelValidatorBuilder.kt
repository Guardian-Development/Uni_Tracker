package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import java.time.LocalDateTime

class ConfigurationDataModelValidatorBuilder(
        var yearStartedMinValue: Int = 1950,
        var yearStartedMaxValue: Int = LocalDateTime.now().year,
        var courseLengthMinValue: Int = 1,
        var courseLengthMaxValue: Int = 15,
        var targetPercentageMinValue: Int = 0,
        var targetPercentageMaxValue: Int = 100,
        var totalCreditsMinValue: Int = 10,
        var totalCreditsMaxValue: Int = 500
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
                    courseLengthMinValue,
                    courseLengthMaxValue,
                    targetPercentageMinValue,
                    targetPercentageMaxValue,
                    totalCreditsMinValue,
                    totalCreditsMaxValue)

    companion object
    {
        fun configurationDataModelValidatorBuilder() : ConfigurationDataModelValidatorBuilder =
                ConfigurationDataModelValidatorBuilder()
    }
}