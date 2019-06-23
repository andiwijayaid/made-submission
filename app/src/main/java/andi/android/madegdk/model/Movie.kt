package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        @SerializedName("poster")
        var poster: String? = null,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("date")
        var date: String? = null,
        @SerializedName("rating")
        var rating: Int? = null,
        @SerializedName("runtime")
        var runtime: Int? = null,
        @SerializedName("budget")
        var budget: Int? = null,
        @SerializedName("revenue")
        var revenue: Int? = null,
        @SerializedName("overview")
        var overview: String? = null
) : Parcelable