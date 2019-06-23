package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(

        @SerializedName("poster")
        private val poster: String? = null,
        @SerializedName("title")
        private val title: String? = null,
        @SerializedName("date")
        private val date: String? = null,
        @SerializedName("rating")
        private val rating: Int? = null,
        @SerializedName("runtime")
        private val runtime: Int? = null,
        @SerializedName("num_of_episode")
        private val numberOfEpisode: Int? = null,
        @SerializedName("language")
        private val language: String? = null,
        @SerializedName("overview")
        private val overview: String? = null

) : Parcelable