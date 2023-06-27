package com.sp0ort365.mawt.ui.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.sp0ort365.mawt.databinding.ItemEventBinding
import com.sp0ort365.mawt.remote.models.bet.GameEvent

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var statList = listOf<GameEvent>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stat = statList[position])
    }

    override fun getItemCount() = statList.size

    inner class ViewHolder(private val binding :ItemEventBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(stat:GameEvent) {
            binding.cardHome.isInvisible = stat.team !="first"
            binding.cardAway.isInvisible = stat.team !="second"
            binding.tvAwayEvent.text = stat.event
            binding.tvHomeEvent.text = stat.event
            binding.textMinute.text = stat.time
        }
    }
}