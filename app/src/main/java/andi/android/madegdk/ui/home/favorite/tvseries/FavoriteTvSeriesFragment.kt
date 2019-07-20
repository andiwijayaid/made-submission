package andi.android.madegdk.ui.home.favorite.tvseries

import andi.android.madegdk.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FavoriteTvSeriesFragment : Fragment() {

    private lateinit var favoriteTvSeriesView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteTvSeriesView = inflater.inflate(R.layout.fragment_favorite_tv_series, container, false)

        return favoriteTvSeriesView

    }

}