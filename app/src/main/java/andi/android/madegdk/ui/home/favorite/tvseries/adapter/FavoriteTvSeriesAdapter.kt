package andi.android.madegdk.ui.home.favorite.tvseries.adapter

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.database.FavoriteTvSeriesHelper
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.utils.normalizeRating
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_tv_series.view.*

class FavoriteTvSeriesAdapter(private val context: Context?, private val listener: (TvSeries) -> Unit) :
        RecyclerView.Adapter<FavoriteTvSeriesViewHolder>() {


    private val mData = ArrayList<TvSeries>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoriteTvSeriesViewHolder {
        return FavoriteTvSeriesViewHolder(
                context,
                LayoutInflater.from(p0.context).inflate(
                        R.layout.item_movie,
                        p0,
                        false
                )
        )
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(p0: FavoriteTvSeriesViewHolder, p1: Int) {
        if (context != null) {
            p0.bindItem(context, mData[p1], listener)
        }
    }

    fun setMovies(movies: ArrayList<TvSeries>) {
        mData.clear()
        mData.addAll(movies)
        notifyDataSetChanged()
    }

}

class FavoriteTvSeriesViewHolder(context: Context?, view: View) : RecyclerView.ViewHolder(view) {

    private val favoriteTvSeriesHelper = FavoriteTvSeriesHelper.getInstance(context)

    fun bindItem(context: Context, tvSeries: TvSeries, listener: (TvSeries) -> Unit) {
        itemView.itemParentCV.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_from_left)
        itemView.titleTV.text = tvSeries.title
        itemView.dateTV.text = tvSeries.firstAirDate
        Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}t/p/w185${tvSeries.poster}")
                .into(itemView.posterIV)
        itemView.ratingBar.rating = normalizeRating(tvSeries.rating)

        itemView.setOnClickListener {
            listener(tvSeries)
        }
        itemView.favoriteBT.setOnClickListener {

            Log.d("FAV", favoriteTvSeriesHelper?.getAllFavoriteTvSeries().toString())
        }
    }
}