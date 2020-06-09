package com.example.newscast.network.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class SourceModel (
    @field:Json(name = "uri") val uri: String?,
    @field:Json(name = "dataType") val dataType: String?,
    @field:Json(name = "title") val title: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeString(dataType)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SourceModel> {
        override fun createFromParcel(parcel: Parcel): SourceModel {
            return SourceModel(parcel)
        }

        override fun newArray(size: Int): Array<SourceModel?> {
            return arrayOfNulls(size)
        }
    }
}