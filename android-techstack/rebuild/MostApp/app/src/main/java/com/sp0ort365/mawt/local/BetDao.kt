package com.sp0ort365.mawt.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BetDao {

    @Query("SELECT * FROM bets")
    fun getAllBets() : Flow<List<Bet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:Bet)
}