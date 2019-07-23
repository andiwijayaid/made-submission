package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSeries(

        @SerializedName("id")
        var tvSeriesId: Int? = null,
        @SerializedName("poster_path")
        val poster: String? = null,
        @SerializedName("backdrop_path")
        val backdrop: String? = null,
        @SerializedName("original_name")
        val title: String? = null,
        @SerializedName("first_air_date")
        val firstAirDate: String? = null,
        @SerializedName("vote_average")
        val rating: Float? = null,
        @SerializedName("overview")
        val overview: String? = null

) : Parcelable