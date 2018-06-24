package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import android.os.Parcel
import android.os.Parcelable
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNull
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange

data class ConfigurationYearWeightingModel(
        val year: Int,
        val weighting: Int,
        val creditsCompletedWithinYear: Int
) : Parcelable
{
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeInt(year)
        parcel.writeInt(weighting)
        parcel.writeInt(creditsCompletedWithinYear)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConfigurationYearWeightingModel>
    {
        override fun createFromParcel(parcel: Parcel): ConfigurationYearWeightingModel
        {
            return ConfigurationYearWeightingModel(parcel)
        }

        override fun newArray(size: Int): Array<ConfigurationYearWeightingModel?>
        {
            return arrayOfNulls(size)
        }
    }
}

class ConfigurationYearWeightingModelValidator(
        val weightingMinimum: Int = 0,
        val weightingMaximum:Int = 100
){
    fun validate(year: Int?, weighting: Int?, creditsCompletedWithinYear: Int?) : Boolean =
            year.notNull() &&
            weighting.notNullWithinInclusiveRange(weightingMinimum, weightingMaximum) &&
            creditsCompletedWithinYear.notNull()
}