package andi.android.madegdk.ui.home.favorite.movie

import andi.android.madegdk.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FavoriteMovieFragment : Fragment() {

    private lateinit var favoriteMovieView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteMovieView = inflater.inflate(R.layout.fragment_favorite_movie, container, true)

        Log.d("FavoriteMovieFragment", "MASUK")

        return favoriteMovieView

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", "MASUK")

    }

}