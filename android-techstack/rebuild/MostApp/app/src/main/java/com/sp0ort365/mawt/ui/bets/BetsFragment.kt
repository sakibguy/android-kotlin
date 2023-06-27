package com.sp0ort365.mawt.ui.bets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationBarView
import com.sp0ort365.mawt.R
import com.sp0ort365.mawt.databinding.FragmentBetsBinding
import com.sp0ort365.mawt.ui.news.NewsFragmentDirections
import com.sp0ort365.mawt.utils.BetHelper


class BetsFragment : Fragment() {
    private var _binding: FragmentBetsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BetsViewModel by viewModels()
    private var adater : BetsAdapter?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBetsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBinding()
        handleBackPressCallback()

    }

    private fun handleBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = BetsFragmentDirections.actionBetsFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        })
    }

    private fun initBinding() {
        binding.bottomNavBet.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when (it.itemId) {
                R.id.statsFragment -> {
                    val action = BetsFragmentDirections.actionBetsFragmentToMyStatsFragment()
                    findNavController().navigate(action)
                }
                R.id.wonFragment -> {
                    val list = BetHelper.betList
                    adater?.updateList(ArrayList(list.filter { it.isWon == true }))
                }
                R.id.lostFragment -> {
                    val list = BetHelper.betList
                    adater?.updateList(ArrayList(list.filter { it.isWon == false }))
                }
            }
            return@OnItemSelectedListener true
        })
    }

    private fun initObservers() {
        adater = BetsAdapter()
        viewModel.betList.observe(viewLifecycleOwner) {
            Log.d("BetsFragment","BetList: $it")
            adater?.betList = ArrayList(it)
            binding.rvBets.adapter = adater
            BetHelper.betList= ArrayList(it)
        }
    }

}