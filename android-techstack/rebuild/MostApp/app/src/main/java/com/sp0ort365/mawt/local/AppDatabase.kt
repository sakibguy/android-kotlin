package com.sp0ort365.mawt.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Bet::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase() {
    abstract fun dao() :BetDao
}