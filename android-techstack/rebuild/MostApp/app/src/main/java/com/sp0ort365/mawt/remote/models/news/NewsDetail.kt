package com.sp0ort365.mawt.remote.models.news

import com.google.gson.annotations.SerializedName


data class NewsDetail(
    @SerializedName("article_body")
    val articleBody: String
)


