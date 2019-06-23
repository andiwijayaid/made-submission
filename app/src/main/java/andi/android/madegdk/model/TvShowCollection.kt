package andi.android.madegdk.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TvShowCollection(
        @SerializedName("movies")
        private val movies: ArrayList<Movie>? = null
) : Parcelable
