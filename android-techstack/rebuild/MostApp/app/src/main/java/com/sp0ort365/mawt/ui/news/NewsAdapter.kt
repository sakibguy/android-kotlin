package com.sp0ort365.mawt.ui.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sp0ort365.mawt.databinding.ItemNewsBinding
import com.sp0ort365.mawt.remote.models.news.MainNew

class NewsAdapter :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var newsList = listOf<MainNew>()
    private var onClick :(item: MainNew) -> Unit = {}
    fun setOnClickListener(listener: (item: MainNew) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news =newsList[position])
    }

    override fun getItemCount() = newsList.size

    inner class NewsViewHolder(private val binding :ItemNewsBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(news: MainNew) {
            binding.tvTitle.text = news.title
            Log.d("NewsAdapter","Url ${news.imageUrl}")
            binding.ivNews.load(news.imageUrl)
            binding.root.setOnClickListener {
                onClick(news)
            }
        }
    }
}