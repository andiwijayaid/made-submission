package andi.android.madegdk.ui.home.tvseries.adapter

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.utils.normalizeRating
import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide


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
    private val titleTV: TextView = view.findViewById(R.id.titleTV)
    private val dateTV: TextView = view.findViewById(R.id.dateTV)
    private val posterIV: ImageView = view.findViewById(R.id.posterIV)
    private val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    private val runtimeTV: TextView = view.findViewById(R.id.runtimeTV)

    @SuppressLint("SetTextI18n")
    fun bindItem(context: Context, tvSeries: TvSeries, listener: (TvSeries) -> Unit) {
        titleTV.text = tvSeries.title
        dateTV.text = tvSeries.firstAirDate
        Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}t/p/w185${tvSeries.poster}")
                .into(posterIV)
        ratingBar.rating = normalizeRating(tvSeries.rating)

        itemView.setOnClickListener {
            listener(tvSeries)
        }
    }
}