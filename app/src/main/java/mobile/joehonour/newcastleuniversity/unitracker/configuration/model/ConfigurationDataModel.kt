package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import android.os.Parcel
import android.os.Parcelable
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange
import java.time.LocalDateTime

data class ConfigurationDataModel(
        val universityName: String,
        val yearStarted: Int,
        val targetPercentage: Int
) : Parcelable
{
   constructor(parcel: Parcel) : this(
           parcel.readString(),
           parcel.readInt(),
           parcel.readInt())

   override fun writeToParcel(parcel: Parcel, flags: Int)
   {
      parcel.writeString(universityName)
      parcel.writeInt(yearStarted)
      parcel.writeInt(targetPercentage)
   }

   override fun describeContents(): Int
   {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<ConfigurationDataModel>
   {
      override fun createFromParcel(parcel: Parcel): ConfigurationDataModel
      {
         return ConfigurationDataModel(parcel)
      }

      override fun newArray(size: Int): Array<ConfigurationDataModel?>
      {
         return arrayOfNulls(size)
      }
   }
}

class ConfigurationDataModelValidator(
        val yearStartedMinValue: Int = 1950,
        val yearStartedMaxValue: Int = LocalDateTime.now().year,
        val targetPercentageMinValue: Int = 0,
        val targetPercentageMaxValue: Int = 100)
{
   fun validate(
           universityName: String?,
           yearStarted: Int?,
           targetPercentage: Int?) : Boolean =
                    universityName.notNullOrEmpty() &&
                    yearStarted.notNullWithinInclusiveRange(
                           yearStartedMinValue, yearStartedMaxValue) &&
                    targetPercentage.notNullWithinInclusiveRange(
                           targetPercentageMinValue, targetPercentageMaxValue)
}