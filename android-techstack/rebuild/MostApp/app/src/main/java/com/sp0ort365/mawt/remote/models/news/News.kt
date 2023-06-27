package com.sp0ort365.mawt.remote.models.news

import com.google.gson.annotations.SerializedName

data class MainNewsResponse (
    val data: Data
)

data class Data (
    val feed: Feed
)

data class Feed (
    val layouts: List<Layout>
)

data class Layout (
    @SerializedName("__typename")
    val typename: String,
    val id: String,
    val type: String,
    val title: Description? = null,
    val description: Description? = null,
    val tag: Map<String, String?>? = null,
    val action: Action? = null,
    val contents: List<Content>?,
    @SerializedName("container_type")
    val containerType: String? = null
)

data class Action (
    @SerializedName("__typename")
    val typename: String,

    @SerializedName("app_linked_string")
    val appLinkedString: String,

    @SerializedName("raw_string")
    val rawString: String
)

data class Content (
    @SerializedName("__typename")
    val typename: String,
    val consumable: Consumable,
    val description: String? = null,
    val id: String,
    @SerializedName("consumable_id")
    val consumableID: String? = null,
    val type: String,
    val title: String? = null
)

data class Consumable (
    @SerializedName("__typename")
    val typename: String,
    val id: String,
    val title: String? = null,
    @SerializedName("image_uri")
    val imageURI: String? = null,
    @SerializedName("primary_tag_string")
    val primaryTagString: String? = null,
    @SerializedName("comment_count")
    val commentCount: Long? = null,
    @SerializedName("lock_comments")
    val lockComments: Boolean? = null,
    @SerializedName("disable_comments")
    val disableComments: Boolean? = null,
    @SerializedName("published_at")
    val publishedAt: Long? = null,
    val permalink: String? = null,
    val excerpt: String? = null,
    @SerializedName("excerpt_plaintext")
    val excerptPlaintext: String? = null,
    @SerializedName("is_read")
    val isRead: Boolean? = null,
    @SerializedName("is_saved")
    val isSaved: Boolean? = null,
    val author: Map<String, String?>? = null,
    val type: String? = null,
    val headline: String? = null,
    @SerializedName("byline_linkable")
    val bylineLinkable: Action? = null,
    @SerializedName("created_at")
    val createdAt: Long? = null,
    val images: List<Image>? = null,
    val importance: String? = null,
    @SerializedName("last_activity_at")
    val lastActivityAt: Long? = null,
    @SerializedName("primary_tag")
    val primaryTag: Map<String, String?>? = null,
    @SerializedName("article_id")
    val articleID: String? = null,
    val article: Article? = null
)


data class Article (
    @SerializedName("__typename")
    val typename: String,
    @SerializedName("comment_count")
    val commentCount: Long,
    @SerializedName("disable_comments")
    val disableComments: Boolean,
    @SerializedName("lock_comments")
    val lockComments: Boolean,
    val excerpt: String,
    @SerializedName("excerpt_plaintext")
    val excerptPlaintext: String,
    val id: String,
    @SerializedName("image_uri")
    val imageURI: String,
    val permalink: String,
    @SerializedName("primary_tag_string")
    val primaryTagString: String,
    @SerializedName("published_at")
    val publishedAt: Long,
    val title: String,
    val author: Map<String, String?>,
    val articleAuthors: List<ArticleAuthor>,
    @SerializedName("is_read")
    val isRead: Boolean,
    @SerializedName("is_saved")
    val isSaved: Boolean
)

data class ArticleAuthor (
    @SerializedName("__typename")
    val typename: String,
    val id: String,
    val author: Map<String, String?>,
    @SerializedName("display_order")
    val displayOrder: Long
)



data class Image (
    @SerializedName("__typename")
    val typename: String,
    val id: String,
    @SerializedName("image_height")
    val imageHeight: Long,
    @SerializedName("image_width")
    val imageWidth: Long,
    @SerializedName("image_uri")
    val imageURI: String,
    @SerializedName("thumbnail_height")
    val thumbnailHeight: Long,
    @SerializedName("thumbnail_width")
    val thumbnailWidth: Long,
    @SerializedName("thumbnail_uri")
    val thumbnailURI: String
)


data class Description (
    @SerializedName("__typename")
    val typename: String,
    @SerializedName("app_text")
    val appText: String? = null
)

data class PageInfo (
    @SerializedName("__typename")
    val typename: String,
    val id: String,
    val currentPage: Long,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)

