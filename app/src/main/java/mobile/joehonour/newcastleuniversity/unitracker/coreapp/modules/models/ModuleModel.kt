package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import android.os.Parcel
import android.os.Parcelable

data class ModuleModel(val moduleId: String,
                       val moduleCode: String,
                       val moduleName: String,
                       val moduleCredits: Int,
                       val moduleYearStudied: Int,
                       val results: List<ModuleResultModel>) : Parcelable
{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.createTypedArrayList(ModuleResultModel))

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(moduleId)
        parcel.writeString(moduleCode)
        parcel.writeString(moduleName)
        parcel.writeInt(moduleCredits)
        parcel.writeInt(moduleYearStudied)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int
    {
        return 0
    }

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

