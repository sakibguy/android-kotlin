package com.sp0ort365.mawt.ui.home.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sp0ort365.mawt.MostApp.Companion.api
import com.sp0ort365.mawt.remote.models.bet.GameEvent
import com.sp0ort365.mawt.remote.models.bet.MatchDetail
import kotlinx.coroutines.launch

class MatchDetailViewModel : ViewModel() {

    val match = MutableLiveData<MatchDetail>()
    val eventList = MutableLiveData<List<GameEvent>>()
    val homeIcon = MutableLiveData<Bitmap?>()
    val awayIcon = MutableLiveData<Bitmap?>()

    fun getMatchDetail(id: String) {
        viewModelScope.launch {
            try {
                val body = api.getSpecificMatch(id)
                if (body.isSuccessful) {
                    match.value = body.body()?.body
                }
            } catch (e: Exception) {

            }
        }
    }

    fun fetchGameEvents(gameId: String) {
        viewModelScope.launch {
            try {
                val body = api.getSpecificGameEvent(gameId)
                if(body.isSuccessful) {
                    eventList.value = body.body()?.body
                }
            } catch (e: java.lang.Exception) {

            }
        }
    }

    fun getHomeIcon(id: String) {
        viewModelScope.launch {
            try {
                val body = api.getTeamIcon(id)
                if (body.isSuccessful) {
                    val bytes = body.body()?.bytes()
                    bytes?.let {
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, it.size)
                        homeIcon.value = bitmap
                    }?:kotlin.run {
                        homeIcon.value = null
                    }
                } else {
                    homeIcon.value= null
                }
            } catch (e: Exception) {
                homeIcon.value= null
            }
        }
    }

    fun getAwayIcon(id: String) {
        viewModelScope.launch {
            try {
                val body = api.getTeamIcon(id)
                if (body.isSuccessful) {
                    val bytes = body.body()?.bytes()
                    bytes?.let {
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, it.size)
                        awayIcon.value = bitmap
                    }?:kotlin.run {
                        awayIcon.value = null
                    }
                } else {
                    awayIcon.value= null
                }
            } catch (e: Exception) {
                awayIcon.value= null
            }
        }
    }


}