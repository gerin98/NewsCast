package com.example.newscast.network.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class ResultsModel(
    @field:Json(name = "uri") val uri: String? = null,
    @field:Json(name = "url") val url: String? = null,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "date") val date: String? = null,
    @field:Json(name = "image") val image: String? = null,
    @field:Json(name = "body") val body: String? = null,
    @field:Json(name = "source") val source: SourceModel? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(SourceModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeString(url)
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(image)
        parcel.writeString(body)
        parcel.writeParcelable(source, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultsModel> {
        override fun createFromParcel(parcel: Parcel): ResultsModel {
            return ResultsModel(parcel)
        }

        override fun newArray(size: Int): Array<ResultsModel?> {
            return arrayOfNulls(size)
        }
    }
}