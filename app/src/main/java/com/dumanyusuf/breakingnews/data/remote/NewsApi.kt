package com.dumanyusuf.breakingnews.data.remote

import com.dumanyusuf.breakingnews.data.remote.dto.NewsDto
import com.dumanyusuf.breakingnews.util.Constans
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query(value = "country") country: String = "us",
        @Query("apiKey") apiKey: String = Constans.API_KEY
    ): NewsDto

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("apiKey") apiKey: String = Constans.API_KEY
    ): NewsDto



}