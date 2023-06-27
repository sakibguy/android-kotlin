package com.sp0ort365.mawt.ui.bets

import androidx.lifecycle.*
import com.sp0ort365.mawt.MostApp.Companion.db
import com.sp0ort365.mawt.local.Bet
import kotlinx.coroutines.launch

class BetsViewModel : ViewModel() {
     lateinit var betList: LiveData<List<Bet>>

    init {
        getBetHistoryList()
    }

    private fun getBetHistoryList() {
        viewModelScope.launch {
            betList = db.dao().getAllBets().asLiveData()
        }
    }
}