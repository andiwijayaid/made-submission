package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("poster_path")
        var poster: String? = null,
        @SerializedName("backdrop_path")
        var backdrop: String? = null,
        @SerializedName("original_title")
        var title: String? = null,
        @SerializedName("release_date")
        var date: String? = null,
        @SerializedName("vote_average")
        var rating: Float? = null,
        @SerializedName("overview")
        var overview: String? = null,
        @SerializedName("adult")
        var isAdult: Boolean? = null
) : Parcelable