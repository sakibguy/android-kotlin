package com.sp0ort365.mawt.remote.models.bet

import android.os.Parcelable
import com.sp0ort365.mawt.utils.enums.SportType
import kotlinx.android.parcel.Parcelize


data class MainMatchDetailResponse(
    val body: MatchDetail
)

@Parcelize
data class MatchDetail(
    val game_id:String,
    val opp_1_name: String,
    val opp_2_name: String,
    val score_full: String,
    val stat_list: List<Stat>,
    val timer: Long,
    val game_oc_list: List<Odd>,
    val sport_name: String,
    val tournament_name :String
) :Parcelable{
    val matchCurrentTime: String
        get() {
            return (timer / 60).toString()
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


