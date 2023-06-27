package com.sp0ort365.mawt.ui.bets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sp0ort365.mawt.MostApp
import com.sp0ort365.mawt.local.Bet
import kotlinx.coroutines.launch

class MyStatsViewModel : ViewModel() {
    lateinit var betList: LiveData<List<Bet>>

    init {
        getBetHistoryList()
    }

    private fun getBetHistoryList() {
        viewModelScope.launch {
            betList = MostApp.db.dao().getAllBets().asLiveData()
        }
    }
}