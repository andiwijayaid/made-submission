package andi.android.madegdk.ui.home.favorite.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.database.FavoriteTvSeriesHelper
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.ui.home.favorite.tvseries.adapter.FavoriteTvSeriesAdapter
import andi.android.madegdk.ui.home.tvseries.detail.TvSeriesDetailActivity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_tv_series.view.*
import java.lang.ref.WeakReference

class FavoriteTvSeriesFragment : Fragment(), LoadFavoriteTvSeriesCallback {
    override fun preExecute() {
        activity?.runOnUiThread { favoriteTvSeriesView.progressBar.visibility = View.VISIBLE }
    }

    override fun postExecute(favoriteTvSeries: ArrayList<TvSeries>?) {
        favoriteTvSeriesView.progressBar.visibility = View.GONE
        if (favoriteTvSeries != null) {
            favoriteTvSeriesAdapter.setTvSeries(favoriteTvSeries)
        }
    }

    companion object {
        const val EXTRA_TV_SERIES = "EXTRA_TV_SERIES"
        const val EXTRA_POSITION = "EXTRA_POSITION"
        const val EXTRA_IS_REMOVED = "IS_REMOVED"
        const val REQUEST_FAVORITE = 888
        const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var favoriteTvSeriesView: View

    lateinit var favoriteTvSeriesAdapter: FavoriteTvSeriesAdapter
    private var favoriteTvSeriesHelper: FavoriteTvSeriesHelper? = null
    private lateinit var favoriteTvSeries: ArrayList<TvSeries>
    private var mSavedInstanceState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (savedInstanceState != null) {
            mSavedInstanceState = savedInstanceState
        }
        favoriteTvSeriesHelper = FavoriteTvSeriesHelper.getInstance(context)
        favoriteTvSeriesHelper?.open()

        favoriteTvSeriesView = inflater.inflate(R.layout.fragment_favorite_tv_series, container, false)

        favoriteTvSeriesAdapter = FavoriteTvSeriesAdapter(context) { tvSeries: TvSeries, position: Int ->
            val intent = Intent(context, TvSeriesDetailActivity::class.java)
            intent.putExtra(EXTRA_TV_SERIES, tvSeries)
            intent.putExtra(EXTRA_POSITION, position)
            startActivityForResult(intent, REQUEST_FAVORITE)
        }

        favoriteTvSeriesView.favoriteTvSeriesRV.layoutManager = GridLayoutManager(context, 3)
        favoriteTvSeriesAdapter.notifyDataSetChanged()
        favoriteTvSeriesView.favoriteTvSeriesRV.adapter = favoriteTvSeriesAdapter

        setData()

        return favoriteTvSeriesView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteTvSeriesAdapter.getTvSeries())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val isRemoved = data.getBooleanExtra(EXTRA_IS_REMOVED, false)
            if (requestCode == REQUEST_FAVORITE && isRemoved) {
                val position = data.getIntExtra(EXTRA_POSITION, 0)
                favoriteTvSeriesAdapter.removeItem(position)
            }
        }
    }

    private class LoadFavoriteTvSeriesAsync(favoriteTvSeriesHelper: FavoriteTvSeriesHelper?, callback: LoadFavoriteTvSeriesCallback) : AsyncTask<Void, Void, ArrayList<TvSeries>>() {
        private val weakFavoriteMovieHelper = WeakReference(favoriteTvSeriesHelper)
        private val weakCallback = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): ArrayList<TvSeries>? {
            return weakFavoriteMovieHelper.get()?.getAllFavoriteTvSeries()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            weakCallback.get()?.preExecute()
        }

        override fun onPostExecute(result: ArrayList<TvSeries>?) {
            super.onPostExecute(result)
            weakCallback.get()?.postExecute(result)
        }

    }

    fun setData() {
        if (mSavedInstanceState == null) {
            Log.d("ASD", "ASD")
            LoadFavoriteTvSeriesAsync(favoriteTvSeriesHelper, this).execute()
        } else {
            Log.d("DSA", "DSA")
            favoriteTvSeries = mSavedInstanceState!!.getParcelableArrayList(EXTRA_STATE)
            favoriteTvSeriesAdapter.setTvSeries(favoriteTvSeries)
        }
    }

}