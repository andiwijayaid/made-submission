package andi.android.madegdk.utils

import android.content.Context

import java.text.DecimalFormat
import java.text.NumberFormat


fun getDrawableId(context: Context, drawableName: String): Int? {
    return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
}

fun convertToCurrency(money: Int?): String {
    val nf = NumberFormat.getCurrencyInstance()
    val pattern = (nf as DecimalFormat).toPattern()
    val newPattern = pattern.replace("\u00A4", "").trim { it <= ' ' }
    val newFormat = DecimalFormat(newPattern)
    return newFormat.format(money)
}

fun isZero(money: Int?): Boolean {
    return money == 0
}

fun convertRatingToFloat(rating: Int?): Float {
    if (rating != null) {
        return rating * 5 / 100f
    }
    return 0F
}