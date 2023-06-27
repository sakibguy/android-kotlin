package com.sp0ort365.mawt.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.sp0ort365.mawt.databinding.ViewMatchBinding

class MatchCardView :MaterialCardView {

    lateinit var binding :ViewMatchBinding
    constructor(context: Context) :super(context) {
        init(context,null)
    }

    constructor(context: Context,attrs:AttributeSet) :super(context,attrs) {
        init(context,attrs)
    }

    fun init(context: Context,attrs: AttributeSet?) {
        binding = ViewMatchBinding.inflate(LayoutInflater.from(context),this,true)
    }

    fun setRedCard(num:String) {
        val oddText = binding.tvRedCard
        oddText.text = num
    }
    fun setYellowCard(yellow:String) {
        val betText = binding.tvYellowCard
        betText.text = yellow
    }
    fun setTeamIcon(bitmap:Bitmap) {
        val image = binding.ivTeam
        image.setImageBitmap(bitmap)
    }


}