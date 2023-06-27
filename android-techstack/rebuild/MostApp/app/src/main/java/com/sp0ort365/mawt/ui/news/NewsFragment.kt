package com.sp0ort365.mawt.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sp0ort365.mawt.databinding.FragmentNewsBinding
import com.sp0ort365.mawt.remote.models.news.MainNew
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        handleBackPressCallback()
    }

    private fun handleBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = NewsFragmentDirections.actionNewsFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        })
    }

    private fun initObservers() {
        val adapter = NewsAdapter()
        val mNewsList = mutableListOf<MainNew>()
        viewModel.newsListLiveData.observe(viewLifecycleOwner) {
            val newsList = it
            Log.d("NewsFragment","newsList $it")
            binding.progressBar.isVisible = newsList.isNullOrEmpty()
            lifecycleScope.launch(Dispatchers.IO) {
                newsList.forEach {
                    it.contents?.let { contents->
                        Log.d("NewsFragment","content: $contents")
                        contents.forEach {
                            mNewsList.add(MainNew(
                                title = it.consumable.title,
                                imageUrl = it.consumable.imageURI,
                                articleId = it.consumable.id
                            ))
                        }
                    }
                }
            }
            Log.d("NewsFragment", "mNewsList $mNewsList")
            adapter.newsList = mNewsList
            binding.rvNews.adapter = adapter
        }
        adapter.setOnClickListener {
            val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}