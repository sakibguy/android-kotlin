package com.sp0ort365.mawt.ui.bets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.sp0ort365.mawt.R
import com.sp0ort365.mawt.databinding.FragmentMyStatsBinding
import com.sp0ort365.mawt.local.Bet
import com.sp0ort365.mawt.utils.DateUtils
import java.util.*

class MyStatsFragment :Fragment() {

    private var _binding :FragmentMyStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel :MyStatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyStatsBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initObservers()
    }

    private fun initBinding() = with(binding) {
        tvDate.text = DateUtils().getCurrentDate()


    }

    private fun initObservers() = with(binding) {
        buttonToggleGroup.check(R.id.button_today)
        viewModel.betList.observe(viewLifecycleOwner) {
            tvDate.text = DateUtils().getCurrentDate()
            val mList=  it.filter { android.text.format.DateUtils.isToday(it.timeStamp) }
            initStatus(mList)
            buttonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                when(checkedId) {
                    R.id.button_today -> {
                        if (isChecked) {
                            tvDate.text = DateUtils().getCurrentDate()
                            val filteredList=  it.filter { android.text.format.DateUtils.isToday(it.timeStamp) }
                            initStatus(filteredList)
                        }
                    }
                    R.id.button_week -> {
                        if (isChecked) {
                            tvDate.text = DateUtils().getOneWeekBefore()+ " - " + DateUtils().getCurrentDate()
                            val filteredList = it.filter { it.timeStamp <System.currentTimeMillis() && it.timeStamp > DateUtils().getPastTimeAsLong(7)}
                            initStatus(filteredList)
                        }
                    }
                    R.id.button_month -> {
                        if (isChecked) {
                            tvDate.text = DateUtils().getCurrentMonth()
                            val filteredList = it.filter { it.timeStamp <System.currentTimeMillis() && it.timeStamp > DateUtils().getPastTimeAsLong(30)}
                            initStatus(filteredList)
                        }
                    }
                    R.id.button_year -> {
                        if (isChecked) {
                            val cal = GregorianCalendar()
                            cal.set(Calendar.DAY_OF_YEAR, 1)
                            val firstDayOfYear = cal.time.time
                            cal.set(Calendar.DAY_OF_YEAR, 366)
                            val lastDayOfYear = cal.time.time
                            tvDate.text = DateUtils().getCurrentYear()
                            val filteredList = it.filter { it.timeStamp in firstDayOfYear..lastDayOfYear }
                            initStatus(filteredList)
                        }
                    }
                    R.id.button_custom -> {
                            if (isChecked) {
                                val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                                    .setTitleText("Select dates")
                                    .build()
                                datePicker.addOnPositiveButtonClickListener {v->
                                    val startDate =datePicker.selection?.first
                                    val endDate =datePicker.selection?.second
                                    val filteredList = it.filter { it.timeStamp in startDate!!..endDate!! }
                                    initStatus(filteredList)
                                }
                                datePicker.addOnDismissListener {
                                    buttonToggleGroup.clearChecked()
                                }
                                datePicker.show(parentFragmentManager,"picker")
                            }
                    }
                }
            }
        }
    }

    private fun initStatus(filteredList: List<Bet>) = with(binding) {
        val filteredWonList=  filteredList.filter { it.isWon == true }
        val filteredLostList=  filteredList.filter {it.isWon == false }
        textBetsTotal.text = getString(R.string.bets_made_size,filteredList.size)
        textBetsWin.text = getString(R.string.won_size,filteredWonList.size)
        textBetsLost.text = getString(R.string.lost_size,filteredLostList.size)
        textEarnedAmount.text = getString(R.string.won_amount,filteredWonList.map { it.amount }.sumOf {it})
        textLostAmount.text = getString(R.string.lost_amount,filteredLostList.map { it.amount }.sumOf {it})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}