package com.sp0ort365.mawt.remote.models.news

data class NewItem(
    val id :Int,
    val publishedDate:String,
    val lasModified: String,
    val type : String,
    val video : NewVideo?,
    val article : NewArticle?,
    val items :List<MultiCardCollection>?,
    val formattedTimestamp :String,
)




