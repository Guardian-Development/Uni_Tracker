package mobile.joehonour.newcastleuniversity.unitracker.model

import android.os.Parcel
import android.os.Parcelable
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange
import java.time.LocalDateTime

data class InitialSetupDataModel(
        val universityName: String,
        val yearStarted: Int,
        val courseLength: Int,
        val targetPercentage: Int,
        val totalCredits: Int
) : Parcelable
{
   constructor(parcel: Parcel) : this(
           parcel.readString(),
           parcel.readInt(),
           parcel.readInt(),
           parcel.readInt(),
           parcel.readInt())

   override fun writeToParcel(parcel: Parcel, flags: Int)
   {
      parcel.writeString(universityName)
      parcel.writeInt(yearStarted)
      parcel.writeInt(courseLength)
      parcel.writeInt(targetPercentage)
      parcel.writeInt(totalCredits)
   }

   override fun describeContents(): Int
   {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<InitialSetupDataModel>
   {
      override fun createFromParcel(parcel: Parcel): InitialSetupDataModel
      {
         return InitialSetupDataModel(parcel)
      }

      override fun newArray(size: Int): Array<InitialSetupDataModel?>
      {
         return arrayOfNulls(size)
      }
   }
}

class InitialSetupDataModelValidator(
        val yearStartedMinValue: Int = 1950,
        val yearStartedMaxValue: Int = LocalDateTime.now().year,
        val courseLengthMinValue: Int = 1,
        val courseLengthMaxValue: Int = 15,
        val targetPercentageMinValue: Int = 0,
        val targetPercentageMaxValue: Int = 100,
        val totalCreditsMinValue: Int = 10,
        val totalCreditsMaxValue: Int = 500)
{
   fun validate(
           universityName: String?,
           yearStarted: Int?,
           courseLength: Int?,
           targetPercentage: Int?,
           totalCredits: Int?) : Boolean =
                    universityName.notNullOrEmpty() &&
                    yearStarted.notNullWithinInclusiveRange(
                           yearStartedMinValue, yearStartedMaxValue) &&
                    courseLength.notNullWithinInclusiveRange(
                           courseLengthMinValue, courseLengthMaxValue) &&
                    targetPercentage.notNullWithinInclusiveRange(
                           targetPercentageMinValue, targetPercentageMaxValue) &&
                    totalCredits.notNullWithinInclusiveRange(
                           totalCreditsMinValue, totalCreditsMaxValue)
}