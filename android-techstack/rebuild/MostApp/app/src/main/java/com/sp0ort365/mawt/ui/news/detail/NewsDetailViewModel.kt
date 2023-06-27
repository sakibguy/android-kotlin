package com.sp0ort365.mawt.ui.news.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sp0ort365.mawt.MostApp.Companion.api
import com.sp0ort365.mawt.MostApp.Companion.newsApi
import com.sp0ort365.mawt.remote.models.news.NewsDetail
import kotlinx.coroutines.launch

class NewsDetailViewModel : ViewModel() {

    val newDetail = MutableLiveData<NewsDetail>()

    fun fetchNewDetail(id: String) {
        viewModelScope.launch {
            try {
                val result = newsApi.getNewDetail(id)
                if (result.isSuccessful) {
                    newDetail.value = result.body()
                }
            } catch (e: Exception) {

            }
        }

    }
}