package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemNewsBinding
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.utils.Utils

class NewsListAdapter :
    ListAdapter<NewsWithCreators, NewsListAdapter.NewsViewHolder>(NewsDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItemWithCreator: NewsWithCreators) = binding.apply {
            newsItemTitleTextView.text = newsItemWithCreator.news.newsItem.title
            newsItemDescriptionTextView.text = newsItemWithCreator.news.newsItem.description
            newsItemDateTextView.text =
                newsItemWithCreator.news.newsItem.publishDate?.let { Utils.showDate(it) }

            setCategory(newsItemWithCreator)

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

        private fun setCategory(newsItemWithCreator: NewsWithCreators) {
            binding.newsItemCategoryTextView.text = newsItemWithCreator.news.category.name
            binding.categoryIconImageView.setImageResource(
                when (newsItemWithCreator.news.category.id) {
                    1 -> R.raw.icon_advertisement
                    2 -> R.raw.icon_bithday
                    3 -> R.raw.icon_salary
                    4 -> R.raw.icon_union
                    5 -> R.raw.icon_holiday
                    6 -> R.raw.icon_massage
                    7 -> R.raw.icon_gratitude
                    8 -> R.raw.icon_help
                    else -> return
                }
            )
        }
    }
}

class NewsDiffCallBack : DiffUtil.ItemCallback<NewsWithCreators>() {
    override fun areItemsTheSame(oldItem: NewsWithCreators, newItem: NewsWithCreators): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewsWithCreators, newItem: NewsWithCreators): Boolean {
        return oldItem == newItem
    }
}