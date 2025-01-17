package com.dumanyusuf.breakingnews.data.repo

import com.dumanyusuf.breakingnews.data.remote.NewsApi
import com.dumanyusuf.breakingnews.data.remote.dto.NewsDto
import com.dumanyusuf.breakingnews.domain.repo.NewsRepo
import javax.inject.Inject


class NewsRepoImpl @Inject constructor(private val api:NewsApi):NewsRepo {

    override suspend fun getNewsList(country: String): NewsDto {
        return api.getBreakingNews(country)
    }

    override suspend fun getBbcNews(): NewsDto {
        return api.getBbcNews()
    }





}