package mobile.joehonour.newcastleuniversity.unitracker.coreapp.modules.models

import android.os.Parcel
import android.os.Parcelable

data class ModuleResultModel(val resultId: String,
                             val resultName: String,
                             val resultWeighting: Int,
                             val resultPercentage: Double) : Parcelable
{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(resultId)
        parcel.writeString(resultName)
        parcel.writeInt(resultWeighting)
        parcel.writeDouble(resultPercentage)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    override fun toString(): String = "result: $resultName with percentage $resultPercentage %"

    companion object CREATOR : Parcelable.Creator<ModuleResultModel>
    {
        override fun createFromParcel(parcel: Parcel): ModuleResultModel
        {
            return ModuleResultModel(parcel)
        }

        override fun newArray(size: Int): Array<ModuleResultModel?>
        {
            return arrayOfNulls(size)
        }
    }
}
