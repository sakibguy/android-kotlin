package com.sp0ort365.mawt.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.sp0ort365.mawt.databinding.ViewOddBinding

class OddView :ConstraintLayout {

    lateinit var binding :ViewOddBinding
    constructor(context: Context) :super(context) {
        init(context,null)
    }

    constructor(context: Context,attrs:AttributeSet) :super(context,attrs) {
        init(context,attrs)
    }

    fun init(context: Context,attrs: AttributeSet?) {
        binding = ViewOddBinding.inflate(LayoutInflater.from(context),this,true)
    }

    fun setOddProperty(odd:String) {
        val oddText = binding.tvOdd
        oddText.text = odd
    }
    fun setBetProperty(bet:String) {
        val betText = binding.tvBet
        betText.text = bet
    }


}