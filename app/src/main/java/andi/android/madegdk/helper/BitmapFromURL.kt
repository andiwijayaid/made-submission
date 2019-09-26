package andi.android.madegdk.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.URL

fun getBitmapFromURL(imageUrl: String): Bitmap? {
    return try {
        val url = URL(imageUrl)
        val connection = url.openConnection()
        connection.doInput = true
        connection.connect()
        val inputStream = connection.getInputStream()
        val mBitmap = BitmapFactory.decodeStream(inputStream)
        mBitmap
    } catch (e: IOException) {
        null
    }
}