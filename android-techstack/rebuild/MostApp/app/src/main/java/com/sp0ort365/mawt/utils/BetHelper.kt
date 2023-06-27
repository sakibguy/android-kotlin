package com.sp0ort365.mawt.utils

import com.sp0ort365.mawt.local.Bet
import com.sp0ort365.mawt.remote.models.bet.Match

class BetHelper {


    fun clear() {
        matchList.clear()
    }

    companion object {
        var matchList :ArrayList<Match> = arrayListOf<Match>()
        set(value)  {
            field= value
        }

        var betList :ArrayList<Bet> = arrayListOf<Bet>()
            set(value)  {
                field= value
            }
    }
}