package com.dumanyusuf.breakingnews.data.di

import com.dumanyusuf.breakingnews.data.remote.NewsApi
import com.dumanyusuf.breakingnews.data.repo.NewsRepoImpl
import com.dumanyusuf.breakingnews.domain.repo.NewsRepo
import com.dumanyusuf.breakingnews.util.Constans
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


   @Provides
   @Singleton
   fun providesRetrofit():NewsApi{
       return Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl(Constans.BASE_URL).build().create(NewsApi::class.java)
   }

    @Provides
    @Singleton
    fun providesRepo(api:NewsApi):NewsRepo{
        return NewsRepoImpl(api)
    }


}