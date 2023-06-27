package com.sp0ort365.mawt.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sp0ort365.mawt.R
import com.sp0ort365.mawt.databinding.FragmentMatchDetailBinding
import com.sp0ort365.mawt.remote.models.bet.GameEvent
import com.sp0ort365.mawt.remote.models.bet.MatchDetail
import com.sp0ort365.mawt.ui.MainActivity
import com.sp0ort365.mawt.ui.bets.BetsFragmentDirections
import com.sp0ort365.mawt.utils.enums.SportType


class MatchDetailFragment : Fragment() {

    private var _binding: FragmentMatchDetailBinding? = null
    private val binding get() = _binding!!
    private val args: MatchDetailFragmentArgs by navArgs()
    private val viewModel: MatchDetailViewModel by viewModels()
    private var homeBet: String? = null
    private var drawBet: String? = null
    private var awayBet: String? = null
    private var statList: List<GameEvent>? = null
    private var matchDetail: MatchDetail? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getMatchDetail(args.matchId)
        viewModel.fetchGameEvents(args.matchId)
        viewModel.getHomeIcon(args.homeIcon.toString())
        viewModel.getAwayIcon(args.awayIcon.toString())
        initObservers()
        handleBackPressCallback()
        makeBet()
    }

    private fun handleBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = MatchDetailFragmentDirections.actionMatchDetailFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        })
    }

    private fun makeBet() = with(binding) {
        odd1.setOnClickListener {
            if (homeBet != null)
                navigateMakeBetScreen("1", homeBet!!)
        }
        odd2.setOnClickListener {
            if (drawBet != null)
                navigateMakeBetScreen("X", drawBet!!)
        }
        odd3.setOnClickListener {
            if (awayBet != null)
                navigateMakeBetScreen("2", awayBet!!)
        }
    }

    private fun navigateMakeBetScreen(odd: String, bet: String) {
        matchDetail?.let {
            val action = MatchDetailFragmentDirections.actionMatchDetailFragmentToMakeBetFragment(
                odd,
                bet,
                args.title,
                it,
                viewModel.homeIcon.value,
                viewModel.awayIcon.value
            )
            findNavController().navigate(action)
        }
    }

    private fun initViews() {
        (requireActivity() as MainActivity).supportActionBar?.title = args.title

        when (args.sportType) {
            SportType.FOOTBALL.sportName -> {
                binding.stadiumLayout.setBackgroundResource(R.drawable.football_bg)
            }
            SportType.BASKETBALL.sportName -> {
                binding.stadiumLayout.setBackgroundResource(R.drawable.basket_bg)
            }
            SportType.HOCKEY.sportName -> {
                binding.stadiumLayout.setBackgroundResource(R.drawable.hockey_bg)
            }
            SportType.TENNIS.sportName -> {
                binding.cardStadium.isVisible = false
            }
        }
        binding.layoutStat.setOnClickListener {
            statList?.let {
                val action = MatchDetailFragmentDirections.actionMatchDetailFragmentToStatsFragment(
                    it.toTypedArray(),
                    args.title
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun initObservers() {
        val adapter = StatsAdapter()
        viewModel.match.observe(viewLifecycleOwner) {
            matchDetail = it
            val stat = it.stat_list.find { it.name == "Possession %" }
            val yellowCard = it.stat_list.find { it.name == "Yellow cards" }
            val redCard = it.stat_list.find { it.name == "Red card" }
            binding.tvHomePlayingStatus.text = "${stat?.opp1}%"
            binding.tvAwayPlayingStatus.text = "${stat?.opp2}%"
            binding.cardHome.setYellowCard(yellowCard?.opp1 ?: "-")
            binding.cardHome.setRedCard(redCard?.opp1 ?: "-")
            binding.cardAway.setYellowCard(yellowCard?.opp2 ?: "-")
            binding.cardAway.setRedCard(redCard?.opp2 ?: "-")
            binding.textTime.text = it.matchCurrentTime
            binding.tvScore.text = it.score_full
            binding.odd1.setOddProperty("1")
            binding.odd3.setOddProperty("2")
            if (it.sport_name == SportType.TENNIS.sportName
                || it.sport_name == SportType.BASKETBALL.sportName
            ) {
                binding.odd3.isVisible = false
                binding.odd2.setOddProperty("2")
            } else {
                binding.odd2.setOddProperty("X")
            }
            binding.odd1.setBetProperty(it.homeOdd ?: "-")
            binding.odd2.setBetProperty(it.drawOdd ?: "-")
            binding.odd3.setBetProperty(it.awayOdd ?: "-")
            homeBet = it.homeOdd
            drawBet = it.drawOdd
            awayBet = it.awayOdd
        }

        viewModel.eventList.observe(viewLifecycleOwner) {
            adapter.statList = it
            statList = it
            binding.rvStats.adapter = adapter
        }

        viewModel.homeIcon.observe(viewLifecycleOwner) {
            it?.let {
                binding.cardHome.setTeamIcon(it)
            }
        }

        viewModel.awayIcon.observe(viewLifecycleOwner) {
            it?.let {
                binding.cardAway.setTeamIcon(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}