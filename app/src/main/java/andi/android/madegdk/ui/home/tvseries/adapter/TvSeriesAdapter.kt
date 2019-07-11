package andi.android.madegdk.ui.home.tvseries.adapter

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.utils.normalizeRating
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_tv_series.view.*


class TvSeriesAdapter(private val context: Context?, private val listener: (TvSeries) -> Unit) :
        RecyclerView.Adapter<TvSeriesViewHolder>() {

    private val mData = ArrayList<TvSeries>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TvSeriesViewHolder {
        return TvSeriesViewHolder(
                LayoutInflater.from(p0.context).inflate(
                        R.layout.item_tv_series,
                        p0,
                        false
                )
        )
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(p0: TvSeriesViewHolder, p1: Int) {
        if (context != null) {
            p0.bindItem(context, mData[p1], listener)
        }
    }

    fun setTvSeries(tvSeries: ArrayList<TvSeries>) {
        mData.clear()
        mData.addAll(tvSeries)
        notifyDataSetChanged()
    }

    fun addTvSeries(tvSeries: ArrayList<TvSeries>) {
        mData.addAll(tvSeries)
        notifyDataSetChanged()
    }

}

class TvSeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(context: Context, tvSeries: TvSeries, listener: (TvSeries) -> Unit) {
        itemView.itemParentCV.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_from_bottom)
        itemView.titleTV.text = tvSeries.title
        itemView.dateTV.text = tvSeries.firstAirDate
        Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}t/p/w185${tvSeries.poster}")
                .into(itemView.posterIV)
        itemView.ratingBar.rating = normalizeRating(tvSeries.rating)

        itemView.setOnClickListener {
            listener(tvSeries)
        }
    }
}