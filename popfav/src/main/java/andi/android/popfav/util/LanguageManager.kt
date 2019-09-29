package andi.android.popfav.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

class LanguageManager(context: Context) {

    var context: Context
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    private val PREF_NAME = "LANGUAGE"
    private val MY_LANG = "MY_LANG"
    private val PRIVATE_MODE = Activity.MODE_PRIVATE

    init {
        this.context = context
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
    }

    fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.getDisplayMetrics())

        editor.putString(MY_LANG, lang)
        editor.apply()
    }

    fun loadLocale() {
        val lang = sharedPreferences.getString(MY_LANG, Locale.getDefault().displayLanguage)
        setLocale(lang)
    }

    fun getMyLang(): String? {
        val myLang = sharedPreferences.getString(MY_LANG, Locale.getDefault().displayLanguage)
        return myLang
    }
}