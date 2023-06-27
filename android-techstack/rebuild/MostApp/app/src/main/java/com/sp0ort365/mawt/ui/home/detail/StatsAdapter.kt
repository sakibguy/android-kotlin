package com.sp0ort365.mawt.ui.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sp0ort365.mawt.databinding.ItemStatBinding
import com.sp0ort365.mawt.remote.models.bet.GameEvent

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.ViewHolder>() {

    var statList = listOf<GameEvent>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stat = statList[position])
    }

    override fun getItemCount() = statList.size

    inner class ViewHolder(private val binding :ItemStatBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(stat:GameEvent) {
            binding.tvStat.text = stat.event
            binding.tvMinute.text = stat.time
        }
    }
}