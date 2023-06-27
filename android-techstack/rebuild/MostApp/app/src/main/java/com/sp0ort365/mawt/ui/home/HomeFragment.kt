package com.sp0ort365.mawt.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.tabs.TabLayout
import com.sp0ort365.mawt.R
import com.sp0ort365.mawt.databinding.FragmentHomeBinding
import com.sp0ort365.mawt.remote.models.bet.Match
import com.sp0ort365.mawt.utils.BetHelper
import com.sp0ort365.mawt.utils.enums.SportType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var httpRequestTime = 0
    private var tabPosition = 0
    private var isLive = true
    private var adapter: MatchAdapter? = null
    private var job: Job?=null
    var homeIconList = arrayListOf<Pair<Int,Bitmap?>?>()
    var awayIconList = arrayListOf<Pair<Int,Bitmap?>?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSelectedListener()
        initLiveUpcomingMatch()
        initObservers()
        initTabAndFetchCategories()
    }

    private fun initLiveUpcomingMatch() {
        binding.buttonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                R.id.button_live -> {
                    if (isChecked) {
                        isLive = true
                        resetMatchList()
                        initMatchListByCategory()
                    }
                }
                R.id.button_upcoming -> {
                    if (isChecked) {
                        isLive = false
                        resetMatchList()
                        initMatchListByCategory()
                    }
                }
            }
        }
    }

    private fun initTabAndFetchCategories() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position ?: 0
                resetMatchList()
                initMatchListByCategory()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun resetMatchList() {
        job?.cancel()
        homeIconList.clear()
        awayIconList.clear()
        adapter?.clear()
        httpRequestTime = 0
        binding.progressBar.isVisible = true
    }

    private fun initMatchListByCategory() {
        when (tabPosition) {
            0 -> {
                if (isLive)
                    viewModel.getLiveMatchByCategory(SportType.FOOTBALL.value)
                else
                    viewModel.getUpcomingMatchByCategory(SportType.FOOTBALL.value)
            }
            1 -> {
                if (isLive)
                    viewModel.getLiveMatchByCategory(SportType.HOCKEY.value)
                else
                    viewModel.getUpcomingMatchByCategory(SportType.HOCKEY.value)
            }
            2 -> {
                if (isLive)
                    viewModel.getLiveMatchByCategory(SportType.BASKETBALL.value)
                else
                    viewModel.getUpcomingMatchByCategory(SportType.BASKETBALL.value)
            }
            3 -> {
                if (isLive)
                    viewModel.getLiveMatchByCategory(SportType.TENNIS.value)
                else
                    viewModel.getUpcomingMatchByCategory(SportType.TENNIS.value)
            }
        }
    }

    private fun initObservers() {
        adapter = MatchAdapter()

        adapter?.setOnClickListener { item, title, sportType ->
            val action = HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(
                item.game_id.toString(),
                item.opp_1_icon,
                item.opp_2_icon,
                title,
                sportType
            )
            findNavController().navigate(action)
        }

        var totalSize = 0


        viewModel.liveSoccerMatchList.observe(viewLifecycleOwner) {
            totalSize = it.size
            adapter?.matchList = it
            binding.rvMatch.setItemViewCacheSize(it.size)
            binding.rvMatch.adapter = adapter
            binding.progressBar.isVisible = false
            Log.d("HomeFragment","ArrayListMatch $it")

             job = lifecycleScope.launch(Dispatchers.IO) {
                 it.forEachIndexed { index, match ->
                     Log.d("HomeFragment","homeIconId: request ${index}")
                     viewModel.getHomeIcon(match.opp_1_icon.toString(),index)
                     delay(750)
                     viewModel.getAwayIcon(match.opp_2_icon.toString(),index)
                 }
            }

        }
        viewModel.homeIcon.observe(viewLifecycleOwner) {
            homeIconList.add(it)
            Log.d("HomeFragment","homeIconId: response ${it?.first} listSize ${homeIconList.size}")
            adapter?.homeIconList = homeIconList
            it?.first?.let {
                adapter?.notifyItemChanged(it,Unit)
            }
        }


        viewModel.awayIcon.observe(viewLifecycleOwner) {
            awayIconList.add(it)
            adapter?.awayIconList = awayIconList
            it?.first?.let {
                adapter?.notifyItemChanged(it,Unit)
            }
        }

    }

    private fun initBottomSelectedListener() {
        binding.bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when (it.itemId) {
                R.id.betsFragment -> {
                    findNavController().navigate(R.id.action_homeFragment_to_betsFragment)
                }
                R.id.calendarFragment -> {
                    if(binding.buttonToggleGroup.checkedButtonId== R.id.button_upcoming && !binding.progressBar.isVisible)
                        openCalendar()
                }
                R.id.newsFragment -> {
                    findNavController().navigate(R.id.action_homeFragment_to_newsFragment)
                }
            }
            return@OnItemSelectedListener true
        })
    }

    private fun openCalendar() {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select dates")
            .build()
        datePicker.addOnPositiveButtonClickListener {
            Log.d("HomeFragment","firstMatchList ${viewModel.liveSoccerMatchList.value}")
            val startDate =datePicker.selection?.first
            val endDate =datePicker.selection?.second
            adapter?.updateList(viewModel.liveSoccerMatchList.value!!,startDate!!,endDate!!)
        }
        datePicker.show(parentFragmentManager,"picker")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        removeObservers()
    }

    private fun removeObservers() {
        viewModel.liveSoccerMatchList.removeObservers(viewLifecycleOwner)
        viewModel.homeIcon.removeObservers(viewLifecycleOwner)
        viewModel.awayIcon.removeObservers(viewLifecycleOwner)
        httpRequestTime = 0
        job?.cancel()
    }

}