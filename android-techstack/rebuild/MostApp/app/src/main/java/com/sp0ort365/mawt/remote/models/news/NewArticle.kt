package com.sp0ort365.mawt.remote.models.news

data class NewArticle(
    val id:Int,
    val headline :String,
    val description :String,
    val url:String,
    val images: ArticleImage?
)

data class ArticleImage(
    val standard:String
)
