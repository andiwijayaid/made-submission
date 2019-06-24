package andi.android.madegdk.ui.home.tvseries.adapter

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.utils.convertRatingToFloat
import andi.android.madegdk.utils.getDrawableId
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


class TvSeriesAdapter(private val context: Context?, private var tvSeries: ArrayList<TvSeries>, private val listener: (TvSeries) -> Unit) :
        RecyclerView.Adapter<TvSeriesViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TvSeriesViewHolder {
        return TvSeriesViewHolder(
                LayoutInflater.from(p0.context).inflate(
                        R.layout.item_movie,
                        p0,
                        false
                )
        )
    }

    override fun getItemCount(): Int = tvSeries.size

    override fun onBindViewHolder(p0: TvSeriesViewHolder, p1: Int) {
        if (context != null) {
            p0.bindItem(context, tvSeries[p1], listener)
        }
    }

}

class TvSeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleTV: TextView = view.findViewById(R.id.titleTV)
    val dateTV: TextView = view.findViewById(R.id.dateTV)
    val posterIV: ImageView = view.findViewById(R.id.posterIV)
    val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    val runtimeTV: TextView = view.findViewById(R.id.runtimeTV)

    @SuppressLint("SetTextI18n")
    fun bindItem(context: Context, tvSeries: TvSeries, listener: (TvSeries) -> Unit) {
        titleTV.setText(tvSeries.title)
        dateTV.setText(tvSeries.date)
        runtimeTV.setText("${tvSeries.runtime} min")
        Glide.with(context)
                .load(getDrawableId(context, tvSeries.poster))
                .into(posterIV)
        ratingBar.rating = convertRatingToFloat(tvSeries.rating)

        itemView.setOnClickListener {
            listener(tvSeries)
        }
    }
}