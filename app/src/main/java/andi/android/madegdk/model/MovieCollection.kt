package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

import java.util.ArrayList

@Parcelize
data class MovieCollection(
        @SerializedName("movies")
        var movies: ArrayList<Movie>? = null
) : Parcelable
