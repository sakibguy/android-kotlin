package com.sp0ort365.mawt.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sp0ort365.mawt.MostApp.Companion.newsApi
import com.sp0ort365.mawt.remote.models.news.Layout
import com.sp0ort365.mawt.remote.models.news.MainNew
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    val newsListLiveData = MutableLiveData<List<Layout>>()

    init {
        fetchNewsList()
    }

    private fun fetchNewsList() {
        viewModelScope.launch {
            try {
                val body = newsApi.getNewsList()
                if (body.isSuccessful) {
                    newsListLiveData.value = body.body()?.data?.feed?.layouts
                }
            } catch (e: Exception) {

            }
        }
    }
}