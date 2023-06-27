package com.sp0ort365.mawt.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "bets")
@Parcelize
data class Bet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gameId: String,
    val amount: Long,
    val bet: String,
    val odd: String,
    val leagueName: String,
    val title: String,
    val timeStamp: Long,
    val homeIcon: String?,
    val awayIcon: String?,
    val homeTeam :String,
    val awayTeam :String,
    val score :String,
    val isWon :Boolean?=null
) : Parcelable {

    private val startTime: String
        get() {
            val date = SimpleDateFormat("HH:mm aa", Locale.getDefault())
            val savedTime = Date(timeStamp )
            return date.format(savedTime)
        }


    private val fullMatchDate: String
        get() {
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val savedTime = Date(timeStamp)
            return date.format(savedTime)
        }

    val fullDate :String
    get() {
        return "$fullMatchDate\n$startTime"
    }
}
