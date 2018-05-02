package mobile.joehonour.newcastleuniversity.unitracker.configuration.model

import android.os.Parcel
import android.os.Parcelable
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullOrEmpty
import mobile.joehonour.newcastleuniversity.unitracker.domain.extensions.notNullWithinInclusiveRange
import java.time.LocalDateTime

/**
 * Provides a model for the users configuration, specifically for binding to a view within the
 * configuration section of the application.
 *
 * @param universityName the university the student attends.
 * @param yearStarted the year the student started university.
 * @param courseLength the length of the course the student is studying.
 * @param targetPercentage the target percentage the student wants to achieve in their degree.
 * @param totalCredits the total credits the user has to complete in order to finish their course.
 */
data class ConfigurationDataModel(
        val universityName: String,
        val yearStarted: Int,
        val courseLength: Int,
        val targetPercentage: Int,
        val totalCredits: Int
) : Parcelable
{
   /**
    * Functionality to allow for this class to be passed between views.
    */
   constructor(parcel: Parcel) : this(
           parcel.readString(),
           parcel.readInt(),
           parcel.readInt(),
           parcel.readInt(),
           parcel.readInt())

   /**
    * Functionality to allow for this class to be passed between views.
    */
   override fun writeToParcel(parcel: Parcel, flags: Int)
   {
      parcel.writeString(universityName)
      parcel.writeInt(yearStarted)
      parcel.writeInt(courseLength)
      parcel.writeInt(targetPercentage)
      parcel.writeInt(totalCredits)
   }

   /**
    * Functionality to allow for this class to be passed between views.
    */
   override fun describeContents(): Int
   {
      return 0
   }

   /**
    * Functionality to allow for this class to be passed between views.
    */
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

/**
 * Provides functionality to validate whether a configuration data model is valid before it has a
 * chance to become persisted to the domain.
 *
 * @param yearStartedMinValue the minimum year a student can start their degree.
 * @param yearStartedMaxValue the maximum year a student can start their degree.
 * @param courseLengthMinValue the minimum course length a student can study for.
 * @param courseLengthMaxValue the maximum course length a student can study for.
 * @param targetPercentageMinValue the minimum target percentage a student can aim to achieve.
 * @param targetPercentageMaxValue the maximum target percentage a student can aim to achieve.
 * @param totalCreditsMinValue the minimum amount of credits a student can complete.
 * @param totalCreditsMaxValue the maximum amount of credits a student can complete.
 */
class ConfigurationDataModelValidator(
        val yearStartedMinValue: Int = 1950,
        val yearStartedMaxValue: Int = LocalDateTime.now().year,
        val courseLengthMinValue: Int = 1,
        val courseLengthMaxValue: Int = 15,
        val targetPercentageMinValue: Int = 0,
        val targetPercentageMaxValue: Int = 100,
        val totalCreditsMinValue: Int = 10,
        val totalCreditsMaxValue: Int = 500)
{
   /**
    * Provides validation for the parameters passed, making sure they fall in their respective
    * ranges and are not null.
    *
    * @param universityName the students currently entered university name.
    * @param yearStarted the students currently entered course year started.
    * @param courseLength the students currently entered course length.
    * @param targetPercentage the students currently entered target percentage.
    * @param totalCredits the students currently entered total credits.
    *
    * @return true if all values are not null and inclusive of their valid ranges, else false.
    */
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