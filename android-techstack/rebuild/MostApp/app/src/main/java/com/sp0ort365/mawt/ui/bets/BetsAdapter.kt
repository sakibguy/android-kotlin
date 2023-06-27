package com.sp0ort365.mawt.ui.bets

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sp0ort365.mawt.R
import com.sp0ort365.mawt.databinding.ItemMyBetBinding
import com.sp0ort365.mawt.local.Bet
import com.sp0ort365.mawt.utils.Util

class BetsAdapter : RecyclerView.Adapter<BetsAdapter.ViewHolder>() {

    var betList = arrayListOf<Bet>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(betList[position])
    }

    override fun getItemCount() = betList.size

    inner class ViewHolder(private val binding: ItemMyBetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context
        fun bind(bet: Bet) {
            binding.ivAwayTeam.setImageBitmap(Util().stringToBitmap(bet.homeIcon))
            binding.ivHomeTeam.setImageBitmap(Util().stringToBitmap(bet.awayIcon))
            binding.textLeagueName.text = bet.leagueName
            binding.textHomeTeam.text = bet.homeTeam
            binding.textAwayTeam.text = bet.awayTeam
            binding.textScore.text = bet.score
            binding.textDate.text = bet.fullDate
            bet.isWon?.let {
                if (it)
                    binding.textAmount.setBackgroundColor(ContextCompat.getColor(context, R.color.won_status))
                else
                    binding.textAmount.setBackgroundColor(ContextCompat.getColor(context,R.color.red))
            }
            binding.textAmount.text = "$${ bet.amount}"
        }
    }


    fun updateList(newList:ArrayList<Bet>) {
        betList.clear()
        betList = newList
        notifyDataSetChanged()
    }
}