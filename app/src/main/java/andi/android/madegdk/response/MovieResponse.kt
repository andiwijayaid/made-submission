package andi.android.madegdk.response

import andi.android.madegdk.model.Movie
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
        @SerializedName("page")
        var page: Int?,

        @SerializedName("results")
        var movies: ArrayList<Movie>?
) : Parcelable