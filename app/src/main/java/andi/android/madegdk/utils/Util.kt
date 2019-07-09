package andi.android.madegdk.utils

import android.content.Context
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


fun getDrawableId(context: Context, drawableName: String?): Int? {
    return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
}

fun convertToCurrency(money: Long?): String {
    val nf = NumberFormat.getCurrencyInstance()
    val pattern = (nf as DecimalFormat).toPattern()
    val newPattern = pattern.replace("\u00A4", "").trim { it <= ' ' }
    val newFormat = DecimalFormat(newPattern)
    return newFormat.format(money)
}

fun isZero(money: Long?): Boolean {
    return money == 0L
}

fun normalizeRating(rating: Float?): Float {
    if (rating != null) {
        return rating.div(2)
    }
    return 0F
}

fun convertRatingToFloat(rating: Int?): Float {
    if (rating != null) {
        return rating * 5 / 100f
    }
    return 0F
}

fun isIndonesian(): Boolean {
    return Locale.getDefault().language == "in"
}