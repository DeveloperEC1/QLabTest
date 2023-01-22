package com.example.qlabtest.data.models

import android.os.Parcel
import android.os.Parcelable

data class FlightsModel(
    var price: String? = "",
    var departuresFrom: String? = "",
    var arrivalTo: String? = "",
    var departuresTime: String? = "",
    var arrivalTime: String? = "",
    var departuresDate: String? = "",
    var arrivalDate: String? = "",
    var isDirect: String? = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(price)
        parcel.writeString(departuresFrom)
        parcel.writeString(arrivalTo)
        parcel.writeString(departuresTime)
        parcel.writeString(arrivalTime)
        parcel.writeString(departuresDate)
        parcel.writeString(arrivalDate)
        parcel.writeString(isDirect)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlightsModel> {
        override fun createFromParcel(parcel: Parcel): FlightsModel {
            return FlightsModel(parcel)
        }

        override fun newArray(size: Int): Array<FlightsModel?> {
            return arrayOfNulls(size)
        }
    }

}
