package com.sp0ort365.mawt.ui.news.detail

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.sp0ort365.mawt.databinding.FragmentNewsDetailBinding


class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NewsDetailFragmentArgs by navArgs()
    private val viewModel : NewsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        args.new.articleId?.let { viewModel.fetchNewDetail(it) }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val new = args.new
        binding.textTitle.text = new.title
        binding.ivNews.load(new.imageUrl)
        initObservers()
    }

    private fun initObservers() {
        viewModel.newDetail.observe(viewLifecycleOwner) {
            binding.textDescription.text = Html.fromHtml(it.articleBody, Html.FROM_HTML_MODE_COMPACT)
        }
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}