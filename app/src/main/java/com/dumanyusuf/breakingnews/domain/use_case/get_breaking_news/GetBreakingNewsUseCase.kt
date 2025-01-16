package com.dumanyusuf.breakingnews.domain.use_case.get_breaking_news

import com.dumanyusuf.breakingnews.data.remote.dto.toArticle
import com.dumanyusuf.breakingnews.domain.model.ArticleModel
import com.dumanyusuf.breakingnews.domain.repo.NewsRepo
import com.dumanyusuf.breakingnews.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import android.util.Log

class GetBreakingNewsUseCase @Inject constructor(private val repo: NewsRepo) {

    fun getBreakingNews(country: String): Flow<Resource<List<ArticleModel>>> = flow {
        try {
            emit(Resource.Loading())
            val newsList = repo.getNewsList(country)
            if (newsList.status.equals("ok")) {
                if (newsList.articles.isNullOrEmpty()) {
                    emit(Resource.Error("No articles found for this country"))
                    Log.e("API Error", "Articles list is empty")
                } else {
                    emit(Resource.Success(newsList.toArticle()))
                    Log.d("API Success", "Retrieved ${newsList.articles.size} articles")
                }
            } else {
                emit(Resource.Error("API Error: ${newsList.status}"))
                Log.e("API Error", "API returned status: ${newsList.status}")
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network Error: Please check your internet connection"))
            Log.e("API Error", "Network Error: ${e.localizedMessage}")
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
            Log.e("API Error", "Unexpected Error: ${e.localizedMessage}")
        }
    }
}