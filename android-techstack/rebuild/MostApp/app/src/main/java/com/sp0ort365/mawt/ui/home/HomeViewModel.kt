package com.sp0ort365.mawt.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sp0ort365.mawt.MostApp.Companion.api
import com.sp0ort365.mawt.remote.models.bet.Match
import com.sp0ort365.mawt.utils.enums.SportType
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    val liveSoccerMatchList = MutableLiveData<ArrayList<Match>>()
    val homeIcon = MutableLiveData<Pair<Int,Bitmap?>?>()
    val awayIcon = MutableLiveData<Pair<Int,Bitmap?>?>()

    init {
        getLiveMatchByCategory(SportType.FOOTBALL.value)
    }

    fun getLiveMatchByCategory(sportId: String) {
        viewModelScope.launch {
            try {
                val body = api.getLiveMatchList(sportId)
                if (body.isSuccessful) {
                    liveSoccerMatchList.value = body.body()?.body
                }
            } catch (e: Exception) {
                Timber.e("Ex:$e")
            }
        }
    }

    fun getUpcomingMatchByCategory(sportId: String) {
        viewModelScope.launch {
            try {
                val body = api.getUpcomingMatchList(sportId)
                if (body.isSuccessful) {
                    liveSoccerMatchList.value = body.body()?.body
                }
            } catch (e: Exception) {
                Timber.e("Ex:$e")
            }
        }
    }

    fun getHomeIcon(id: String,position:Int) {
        viewModelScope.launch {
            try {
                val body = api.getTeamIcon(id)
                if (body.isSuccessful) {
                    val bytes = body.body()?.bytes()
                    bytes?.let {
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, it.size)
                        homeIcon.value = Pair(position,bitmap)
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

    fun getAwayIcon(id: String,position: Int) {
        viewModelScope.launch {
            try {
                val body = api.getTeamIcon(id)
                if (body.isSuccessful) {
                    val bytes = body.body()?.bytes()
                    bytes?.let {
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, it.size)
                        awayIcon.value = Pair(position,bitmap)
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