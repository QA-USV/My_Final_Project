package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.NewsListCardBinding
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.utils.Utils

interface NewsOnInteractionListener {
    fun onOpenCard(newsItem: News) {}
}

class NewsListAdapter : ListAdapter<News, NewsListAdapter.NewsViewHolder>(NewsDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListAdapter.NewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NewsViewHolder(
        private val binding: NewsListCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItem: News) = binding.apply {
            newsItemTitleTextView.text = newsItem.title
            newsItemDescriptionTextView.text = newsItem.description
            newsItemDateTextView.text = Utils.convertDate(newsItem.publishDate)

            setCategory(newsItem.newsCategoryId)

            newsItemMaterialCardView.setOnClickListener {
                when (newsItemGroup.visibility) {
                    View.GONE -> {
                        newsItemGroup.visibility = View.VISIBLE
                        viewNewsItemImageView.setImageResource(R.drawable.expand_less_24)
                    }
                    else -> {
                        newsItemGroup.visibility = View.GONE
                        viewNewsItemImageView.setImageResource(R.drawable.expand_more_24)
                    }
                }
            }
        }

        private fun setCategory(category: News.Category) {
            binding.newsItemCategoryTextView.text = itemView.context.getString(category.name)
            binding.categoryIconImageView.setImageResource(category.icon)
        }
    }
}

class NewsDiffCallBack : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}
