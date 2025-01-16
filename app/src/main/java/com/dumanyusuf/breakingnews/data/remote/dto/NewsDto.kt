package com.dumanyusuf.breakingnews.data.remote.dto

import com.dumanyusuf.breakingnews.domain.model.ArticleModel

data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

fun NewsDto.toArticle():List<ArticleModel>{
    return articles.map {
        ArticleModel(
            author = it.author ?: "",
            content = it.content ?: "",
            description = it.description ?: "",
            publishedAt = it.publishedAt ?: "",
            title = it.title ?: "",
            url = it.url ?: "",
            urlToImage = it.urlToImage ?: ""
        )
    }
}