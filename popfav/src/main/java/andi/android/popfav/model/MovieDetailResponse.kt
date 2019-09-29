package andi.android.popfav.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailResponse(
        @SerializedName("budget")
        var budget: String?,

        @SerializedName("revenue")
        var revenue: String?,

        @SerializedName("runtime")
        var runtime: Int?
) : Parcelable