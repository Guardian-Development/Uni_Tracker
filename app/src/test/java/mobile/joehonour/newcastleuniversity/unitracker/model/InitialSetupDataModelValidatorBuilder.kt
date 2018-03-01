package mobile.joehonour.newcastleuniversity.unitracker.model

import java.time.LocalDateTime

class InitialSetupDataModelValidatorBuilder(
        var yearStartedMinValue: Int = 1950,
        var yearStartedMaxValue: Int = LocalDateTime.now().year,
        var courseLengthMinValue: Int = 1,
        var courseLengthMaxValue: Int = 15,
        var targetPercentageMinValue: Int = 0,
        var targetPercentageMaxValue: Int = 100,
        var totalCreditsMinValue: Int = 10,
        var totalCreditsMaxValue: Int = 500
) {

    fun withEdits(builder: InitialSetupDataModelValidatorBuilder.() -> Unit)
            : InitialSetupDataModelValidatorBuilder {
        builder.invoke(this)
        return this
    }

    fun build() : InitialSetupDataModelValidator =
            InitialSetupDataModelValidator(
                    yearStartedMinValue,
                    yearStartedMaxValue,
                    courseLengthMinValue,
                    courseLengthMaxValue,
                    targetPercentageMinValue,
                    targetPercentageMaxValue,
                    totalCreditsMinValue,
                    totalCreditsMaxValue)

    companion object {
        fun initialSetupDataModelValidatorBuilder() : InitialSetupDataModelValidatorBuilder =
                InitialSetupDataModelValidatorBuilder()
    }
}