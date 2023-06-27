package com.sp0ort365.mawt.remote.models.bet

import android.os.Parcelable
import com.sp0ort365.mawt.utils.enums.SportType
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


data class MainMatchResponse(
    val body: ArrayList<Match>
)

@Parcelize
data class Match(
    val opp_1_name: String,
    val opp_2_name: String,
    val game_start: Long,
    val game_id: Long,
    val game_oc_list: List<Odd>,
    val opp_1_icon: Int,
    val opp_2_icon: Int,
    val tournament_name: String,
    val score_full: String,
    val sport_name: String,
) : Parcelable {

    val startTime: String
        get() {
            val date = SimpleDateFormat("HH:mm aa", Locale.getDefault())
            val savedTime = Date(game_start * 1000)
            return date.format(savedTime)
        }

    val startDate: String
        get() {
            val date = SimpleDateFormat("dd/MM", Locale.getDefault())
            val savedTime = Date(game_start * 1000)
            return date.format(savedTime)
        }

    val fullMatchDate: String
        get() {
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val savedTime = Date(game_start * 1000)
            return date.format(savedTime)
        }

    val homeOdd: String?
        get() {
            val oddList =
                if (sport_name == SportType.BASKETBALL.sportName) game_oc_list.filter { it.oc_group_name == "Total" } else game_oc_list.filter { it.oc_group_name == "1x2" }

            return if (oddList.isEmpty()) null else oddList.first().oc_rate.toString()
        }

    val drawOdd: String?
        get() {
            val oddList =
                if (sport_name == SportType.BASKETBALL.sportName) game_oc_list.filter { it.oc_group_name == "Total" } else game_oc_list.filter { it.oc_group_name == "1x2" }
            return if (oddList.isEmpty() || oddList.size < 2) null else oddList[1].oc_rate.toString()
        }

    val awayOdd: String?
        get() {
            val oddList =
                if (sport_name == SportType.BASKETBALL.sportName) game_oc_list.filter { it.oc_group_name == "Total" } else game_oc_list.filter { it.oc_group_name == "1x2" }
            return if (oddList.isEmpty() || oddList.size < 3) null else oddList.last().oc_rate.toString()
        }

}
