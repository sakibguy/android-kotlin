package com.sp0ort365.mawt.remote.models.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MainNew(
    val articleId:String?,
    val title :String?,
    val imageUrl :String?
) :Parcelable
