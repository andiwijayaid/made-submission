package andi.android.madegdk.model

import andi.android.madegdk.database.DatabaseContract.Companion.getColumnFloat
import andi.android.madegdk.database.DatabaseContract.Companion.getColumnInt
import andi.android.madegdk.database.DatabaseContract.Companion.getColumnString
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.BACKDROP
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.FIRST_AIR_DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.OVERVIEW
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.POSTER
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.RATING
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TITLE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TV_SERIES_ID
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSeries(
        @SerializedName("id")
        var tvSeriesId: Int? = null,
        @SerializedName("poster_path")
        var poster: String? = null,
        @SerializedName("backdrop_path")
        var backdrop: String? = null,
        @SerializedName("original_name")
        var title: String? = null,
        @SerializedName("first_air_date")
        var firstAirDate: String? = null,
        @SerializedName("vote_average")
        var rating: Float? = null,
        @SerializedName("overview")
        var overview: String? = null
) : Parcelable {
    constructor(cursor: Cursor) : this() {
        tvSeriesId = getColumnInt(cursor, TV_SERIES_ID)
        poster = getColumnString(cursor, POSTER)
        backdrop = getColumnString(cursor, BACKDROP)
        title = getColumnString(cursor, TITLE)
        firstAirDate = getColumnString(cursor, FIRST_AIR_DATE)
        rating = getColumnFloat(cursor, RATING)
        overview = getColumnString(cursor, OVERVIEW)
    }
}