package com.dumanyusuf.breakingnews.domain.repo

import com.dumanyusuf.breakingnews.data.remote.dto.NewsDto

interface NewsRepo {


     suspend fun getNewsList(country:String):NewsDto


}