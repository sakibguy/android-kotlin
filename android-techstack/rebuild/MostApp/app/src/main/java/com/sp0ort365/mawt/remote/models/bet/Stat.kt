package com.sp0ort365.mawt.remote.models.bet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stat(
    val name :String,
    val opp1:String,
    val opp2:String
) : Parcelable
