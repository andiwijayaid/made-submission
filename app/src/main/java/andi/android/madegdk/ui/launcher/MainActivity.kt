package andi.android.madegdk.ui.launcher

import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.database.FavoriteTvSeriesHelper
import andi.android.madegdk.ui.home.HomeActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var favoriteTvSeriesHelper: FavoriteTvSeriesHelper? = null
    private var favoriteMovieHelper: FavoriteMovieHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteTvSeriesHelper = FavoriteTvSeriesHelper.getInstance(applicationContext)
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(applicationContext)

        favoriteTvSeriesHelper?.open()
        favoriteMovieHelper?.open()

        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
//        favoriteTvSeriesHelper?.close()
    }

}