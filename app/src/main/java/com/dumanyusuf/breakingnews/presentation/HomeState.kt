package com.dumanyusuf.breakingnews.presentation

import android.provider.MediaStore.Audio.Artists
import com.dumanyusuf.breakingnews.domain.model.ArticleModel

data class HomeState(

    val newList:List<ArticleModel> = emptyList(),
    val isLoading:Boolean=false,
    val error:String=""

)
