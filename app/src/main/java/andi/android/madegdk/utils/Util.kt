package andi.android.madegdk.utils

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun convertToCurrency(money: String?): String {
    val nf = NumberFormat.getCurrencyInstance()
    val pattern = (nf as DecimalFormat).toPattern()
    val newPattern = pattern.replace("\u00A4", "").trim { it <= ' ' }
    val newFormat = DecimalFormat(newPattern)
    return newFormat.format(money?.toLong())
}

fun isZero(money: String?): Boolean {
    return money == "0.00"
}

fun normalizeRating(rating: Float?): Float {
    if (rating != null) {
        return rating.div(2)
    }
    return 0F
}

fun convertToRupiah(dollar: String): String {
    return (dollar.toLong() * 14000).toString()
}

fun isIndonesian(myLang: String?): Boolean {
    return myLang == "in"
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return dateFormat.format(calendar)
}