package com.example.newscast.network.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class ArticleModel(
    @field:Json(name = "totalResults") val totalResults: String? = null,
    @field:Json(name = "page") val page: String? = null,
    @field:Json(name = "count") val count: String? = null,
    @field:Json(name = "pages") val pages: String? = null,
    @field:Json(name = "results") val results: List<ResultsModel?>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(ResultsModel)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(totalResults)
        parcel.writeString(page)
        parcel.writeString(count)
        parcel.writeString(pages)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleModel> {
        override fun createFromParcel(parcel: Parcel): ArticleModel {
            return ArticleModel(parcel)
        }

        override fun newArray(size: Int): Array<ArticleModel?> {
            return arrayOfNulls(size)
        }
    }
}