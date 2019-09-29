package andi.android.madegdk.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class FirstOpenPreference(context: Context) {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(FIRST_OPEN_PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        private const val FIRST_OPEN_PREFERENCES = "FIRST_OPEN_PREFERENCES"
        private const val IS_FIRST_OPEN = "IS_FIRST_OPEN"
    }

    fun setFirstOpenStatus(isFirstOpen: Boolean) {
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(IS_FIRST_OPEN, isFirstOpen)
        editor?.apply()
    }

    fun isFirstOpenStatus(): Boolean? {
        return sharedPreferences?.getBoolean(IS_FIRST_OPEN, true)
    }
}