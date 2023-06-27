package com.sp0ort365.mawt.remote.models.bet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class MainGameEvent(
    val body:List<GameEvent>
)

@Parcelize
data class GameEvent(
    val team :String?,
    val time :String?,
    val event :String?,
) :Parcelable