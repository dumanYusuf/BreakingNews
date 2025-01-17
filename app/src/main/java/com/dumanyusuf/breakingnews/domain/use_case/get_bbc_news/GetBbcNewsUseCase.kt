package com.dumanyusuf.breakingnews.domain.use_case.get_bbc_news

import android.util.Log
import com.dumanyusuf.breakingnews.data.remote.dto.toArticle
import com.dumanyusuf.breakingnews.domain.model.ArticleModel
import com.dumanyusuf.breakingnews.domain.repo.NewsRepo
import com.dumanyusuf.breakingnews.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBbcNewsUseCase @Inject constructor(private val repo: NewsRepo){


     fun getBbcNews():Flow<Resource<List<ArticleModel>>> = flow {
       try {
           val list=repo.getBbcNews()
           if (list.status.equals("ok")){
               emit(Resource.Success(list.toArticle()))
           }
           else{
               emit(Resource.Error("data not found"))
           }
       }
       catch (e:Exception){
           emit(Resource.Error("hata:${e.localizedMessage}"))
           Log.e("hata cıktı","hata mesajı:${e.localizedMessage}")
       }
    }


}