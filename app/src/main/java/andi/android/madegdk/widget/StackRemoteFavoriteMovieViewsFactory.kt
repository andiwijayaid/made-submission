package andi.android.madegdk.widget

import andi.android.madegdk.R
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class StackRemoteFavoriteMovieViewsFactory(private val mContext: Context): RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = arrayListOf<Bitmap>()

    override fun onCreate() {}

    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.ic_indonesian))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.ic_english))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.ic_launcher_foreground))
    }

    override fun onDestroy() {}

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.widget_favorite_movie_item)
        remoteViews.setImageViewBitmap(
                R.id.imageView,
                mWidgetItems.get(position)
        )

        val extras = Bundle()
        extras.putInt(mContext.packageName, R.layout.widget_favorite_movie_item)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}