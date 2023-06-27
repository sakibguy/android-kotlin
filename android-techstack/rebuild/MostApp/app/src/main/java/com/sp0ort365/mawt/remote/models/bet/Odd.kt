package com.sp0ort365.mawt.remote.models.bet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Odd(
    val oc_group_name :String,
    val oc_name :String,
    val oc_rate:Float
): Parcelable