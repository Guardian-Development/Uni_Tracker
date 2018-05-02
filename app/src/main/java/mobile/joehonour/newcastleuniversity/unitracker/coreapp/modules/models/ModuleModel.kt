package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Provides a model for a module configured by the student, specifically to be displayed within the
 * core app module section of the application.
 *
 * @param moduleCode the code of the module.
 * @param moduleName the name of the module.
 * @param moduleCredits the credits of the module.
 * @param moduleYearStudied the year the module was studied within the degree course.
 * @param results the results associated with this module.
 */
data class ModuleModel(val moduleCode: String,
                       val moduleName: String,
                       val moduleCredits: Int,
                       val moduleYearStudied: Int,
                       val results: List<ModuleResultModel>) : Parcelable
{
    /**
     * Functionality to allow for this class to be passed between views.
     */
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.createTypedArrayList(ModuleResultModel))

    /**
     * Functionality to allow for this class to be passed between views.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(moduleCode)
        parcel.writeString(moduleName)
        parcel.writeInt(moduleCredits)
        parcel.writeInt(moduleYearStudied)
        parcel.writeTypedList(results)
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
    companion object CREATOR : Parcelable.Creator<ModuleModel>
    {
        override fun createFromParcel(parcel: Parcel): ModuleModel
        {
            return ModuleModel(parcel)
        }

        override fun newArray(size: Int): Array<ModuleModel?>
        {
            return arrayOfNulls(size)
        }
    }
}

