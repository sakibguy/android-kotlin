package com.sp0ort365.mawt.ui.bets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sp0ort365.mawt.MostApp
import com.sp0ort365.mawt.local.Bet
import kotlinx.coroutines.launch

class MakeBetViewModel :ViewModel() {

    fun insertHistory(bet: Bet){
        viewModelScope.launch {
            val db = MostApp.db
            db.dao().insert(bet)
        }
    }

}