package andi.android.madegdk.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSeriesDetailResponse(
        @SerializedName("number_of_episodes")
        var numberOfEpisodes: String?,

        @SerializedName("number_of_seasons")
        var numberOfSeasons: String?
) : Parcelable