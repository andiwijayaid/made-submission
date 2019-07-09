package andi.android.madegdk.response

import andi.android.madegdk.model.TvSeries
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSeriesResponse(
        @SerializedName("page")
        var page: Int?,

        @SerializedName("results")
        var tvSeries: ArrayList<TvSeries>?
) : Parcelable