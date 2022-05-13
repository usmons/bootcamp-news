package io.usmon.bootcampnews.presentation.news_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.usmon.bootcampnews.databinding.ItemNewsListBinding
import io.usmon.bootcampnews.domain.model.local.entitiy.News

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemNewsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBindView(news: News) {
            binding.apply {
                title.text = news.title
                desc.text = news.description
                root.setOnClickListener {
                    onItemClick?.invoke(news)
                }
                more.setOnClickListener {
                    onMoreClick?.invoke(news, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(differ.currentList.get(position))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.newsId == oldItem.newsId
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description
        }
    })

    private var onItemClick: ((News) -> Unit)? = null

    fun onItemClickListener(onClick: (News) -> Unit) {
        onItemClick = onClick
    }

    private var onMoreClick: ((News, View) -> Unit)? = null

    fun onMoreClickListener(onClick: (News, View) -> Unit) {
        onMoreClick = onClick
    }
}