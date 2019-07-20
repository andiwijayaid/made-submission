package andi.android.madegdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
        var id: Int? = null,
        var favorite_id: String? = null
) : Parcelable