package andi.android.madegdk.model

import andi.android.madegdk.database.DatabaseContract.Companion.getColumnFloat
import andi.android.madegdk.database.DatabaseContract.Companion.getColumnInt
import andi.android.madegdk.database.DatabaseContract.Companion.getColumnString
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.BACKDROP
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.OVERVIEW
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.POSTER
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.RATING
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TITLE
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        @SerializedName("id")
        var movieId: Int? = null,
        @SerializedName("poster_path")
        var poster: String? = null,
        @SerializedName("backdrop_path")
        var backdrop: String? = null,
        @SerializedName("original_title")
        var title: String? = null,
        @SerializedName("release_date")
        var date: String? = null,
        @SerializedName("vote_average")
        var rating: Float? = null,
        @SerializedName("overview")
        var overview: String? = null
) : Parcelable {
    constructor(cursor: Cursor) : this() {
        movieId = getColumnInt(cursor, MOVIE_ID)
        poster = getColumnString(cursor, POSTER)
        backdrop = getColumnString(cursor, BACKDROP)
        title = getColumnString(cursor, TITLE)
        date = getColumnString(cursor, DATE)
        rating = getColumnFloat(cursor, RATING)
        overview = getColumnString(cursor, OVERVIEW)
    }
}