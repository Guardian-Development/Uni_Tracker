package mobile.joehonour.newcastleuniversity.unitracker.model

class InitialSetupYearWeightingModelValidatorBuilder(
        var weightingMinimum: Int = 0,
        var weightingMaximum:Int = 100
) {
    fun withEdits(builder: InitialSetupYearWeightingModelValidatorBuilder.() -> Unit)
            : InitialSetupYearWeightingModelValidatorBuilder
    {
        builder.invoke(this)
        return this
    }

    fun build() : InitialSetupYearWeightingModelValidator =
            InitialSetupYearWeightingModelValidator(
                    weightingMinimum,
                    weightingMaximum)

    companion object
    {
        fun initialSetupYearWeightingModelValidatorBuilder() : InitialSetupYearWeightingModelValidatorBuilder =
                InitialSetupYearWeightingModelValidatorBuilder()
    }
}