package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSeries(

        @SerializedName("poster")
        val poster: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("date")
        val date: String? = null,
        @SerializedName("rating")
        val rating: Int? = null,
        @SerializedName("runtime")
        val runtime: Int? = null,
        @SerializedName("num_of_episode")
        val numberOfEpisode: Int? = null,
        @SerializedName("language")
        val language: String? = null,
        @SerializedName("overview")
        val overview: String? = null

) : Parcelable