package com.sp0ort365.mawt.ui.bets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sp0ort365.mawt.databinding.FragmentMakeBetBinding
import com.sp0ort365.mawt.local.Bet
import com.sp0ort365.mawt.ui.MainActivity
import com.sp0ort365.mawt.utils.Util

class MakeBetFragment : Fragment() {
    private var _binding: FragmentMakeBetBinding? = null
    private val binding get() = _binding!!
    private val args: MakeBetFragmentArgs by navArgs()
    private val viewModel: MakeBetViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeBetBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() = with(binding) {
        val match = args.match
        (requireActivity() as MainActivity).supportActionBar?.title = args.title
        val yellowCard = match.stat_list.find { it.name == "Yellow cards" }
        val redCard = match.stat_list.find { it.name == "Red card" }
        cardHome.setYellowCard(yellowCard?.opp1 ?: "-")
        cardHome.setRedCard(redCard?.opp1 ?: "-")
        cardAway.setYellowCard(yellowCard?.opp2 ?: "-")
        cardAway.setRedCard(redCard?.opp2 ?: "-")
        textTime.text = match.matchCurrentTime
        tvScore.text = match.score_full
        includedBetLayout.selectedOddView.setBetProperty(args.bet)
        includedBetLayout.selectedOddView.setOddProperty(args.odd)
        args.homeIcon?.let { cardHome.setTeamIcon(it) }
        args.awayIcon?.let { cardAway.setTeamIcon(it) }
    }

    private fun initListeners() {
        val match = args.match
        binding.includedBetLayout.btnMakeBet.setOnClickListener {
            val amount = binding.includedBetLayout.editTextBet.text?.toString()
            if (!amount.isNullOrEmpty()) {
                viewModel.insertHistory(
                    Bet(
                        gameId = match.game_id,
                        amount = amount.toLong(),
                        odd = args.odd,
                        bet = args.bet,
                        leagueName = match.tournament_name,
                        title = args.title,
                        timeStamp = System.currentTimeMillis(),
                        homeIcon = Util().bitmapToString(args.homeIcon),
                        awayIcon = Util().bitmapToString(args.awayIcon),
                        homeTeam = match.opp_1_name,
                        awayTeam = match.opp_2_name,
                        score = match.score_full
                    )
                )
                val action = MakeBetFragmentDirections.actionMakeBetFragmentToBetsFragment()
                findNavController().navigate(action)
            } else
                Toast.makeText(requireContext(), "Please enter amount!", Toast.LENGTH_SHORT).show()
        }
    }

}