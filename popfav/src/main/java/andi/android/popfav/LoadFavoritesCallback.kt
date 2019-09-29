package andi.android.popfav

import android.database.Cursor

interface LoadFavoritesCallback {
    fun postExecute(favorites: Cursor)
}