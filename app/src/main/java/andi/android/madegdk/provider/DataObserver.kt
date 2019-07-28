package andi.android.madegdk.provider

import andi.android.madegdk.ui.home.favorite.movie.FavoriteMovieFragment
import andi.android.madegdk.ui.home.favorite.movie.LoadFavoriteMoviesCallback
import andi.android.madegdk.ui.home.favorite.tvseries.FavoriteTvSeriesFragment
import andi.android.madegdk.ui.home.favorite.tvseries.LoadFavoriteTvSeriesCallback
import android.content.Context
import android.database.ContentObserver
import android.os.Handler

class DataObserver(handler: Handler, private val context: Context?) : ContentObserver(handler) {
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        if (context != null) {
            FavoriteMovieFragment.LoadFavoriteMoviesAsync(context, context as LoadFavoriteMoviesCallback).execute()
            FavoriteTvSeriesFragment.LoadFavoriteTvSeriesAsync(context, context as LoadFavoriteTvSeriesCallback).execute()
        }
    }
}