package com.sp0ort365.mawt.ui.home

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sp0ort365.mawt.R
import com.sp0ort365.mawt.databinding.ItemMatchBinding
import com.sp0ort365.mawt.remote.models.bet.Match
import com.sp0ort365.mawt.utils.enums.SportType
import java.lang.Exception

class MatchAdapter : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    var matchList = arrayListOf<Match>()
    var homeIconList = arrayListOf<Pair<Int,Bitmap?>?>()
    var awayIconList = arrayListOf<Pair<Int,Bitmap?>?>()

    private var onClick: (item: Match, title: String,sportType:String) -> Unit =
        { match: Match, title: String, sportType:String -> }

    fun setOnClickListener(listener: (item: Match, title: String,sportType:String) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchList[position])
    }

    override fun getItemCount() = matchList.size

    inner class ViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context
        fun bind(match: Match) {
            val fullMatchTime = "${match.fullMatchDate}\n${match.startTime}"
            binding.textHomeTeam.text = match.opp_1_name
            binding.textAwayTeam.text = match.opp_2_name
            binding.textLeagueName.text = match.tournament_name
            binding.textScore.text = match.score_full
            binding.textDate.text = fullMatchTime

            try {
                Log.d("MatchAdapter","AdapterPos: $adapterPosition iconPos: ${homeIconList[adapterPosition]?.first}")
                if (adapterPosition == homeIconList[adapterPosition]?.first) {
                    homeIconList[adapterPosition]?.second?.let {
                        binding.ivHomeTeam.setImageBitmap(it)
                    } ?: kotlin.run {
                        binding.ivHomeTeam.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                    }
                }
                if (adapterPosition == awayIconList[adapterPosition]?.first) {
                    awayIconList[adapterPosition]?.second?.let {
                        binding.ivAwayTeam.setImageBitmap(it)
                    } ?: kotlin.run {
                        binding.ivAwayTeam.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                    }
                }
            } catch (e:Exception) {
                Log.d("MatchAdapter","Exception $e")
            }


            match.homeOdd?.let {
                binding.odd1.isVisible = true
                binding.odd1.setBetProperty(it)
            } ?: kotlin.run {
                binding.odd1.isVisible = false
            }
            match.drawOdd?.let {
                binding.odd2.isVisible = true
                binding.odd2.setBetProperty(it)
            } ?: kotlin.run {
                binding.odd2.isVisible = false
            }

            match.awayOdd?.let {
                binding.odd3.isVisible = true
                binding.odd3.setBetProperty(it)
            } ?: kotlin.run {
                binding.odd3.isVisible = false
            }

            if (match.sport_name == SportType.TENNIS.sportName
                || match.sport_name == SportType.BASKETBALL.sportName
            ) {
                binding.odd3.isVisible = false
                binding.odd2.setOddProperty("2")
            } else {
                binding.odd2.setOddProperty("X")
            }

            binding.odd1.setOddProperty("1")
            binding.odd3.setOddProperty("2")
            binding.root.setOnClickListener {
                onClick(
                    match,
                    "${match.opp_1_name}|${match.opp_2_name}",
                    match.sport_name
                )
            }
        }
    }

    fun clear() {
        matchList.clear()
        homeIconList.clear()
        awayIconList.clear()
        notifyDataSetChanged()
    }

    fun updateList(list:ArrayList<Match>,startDate:Long,endDate:Long) {
       val filteredList = list.filterIndexed { index, match ->
            return@filterIndexed (match.game_start*1000) >= startDate && (match.game_start*1000) <= endDate
        }
        matchList = ArrayList(filteredList)
        notifyDataSetChanged()
    }
}