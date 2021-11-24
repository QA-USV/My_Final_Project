package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemNewsBinding
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.extensions.getType
import ru.netology.fmhandroid.utils.Utils

class NewsListAdapter :
    ListAdapter<NewsWithCreators, NewsListAdapter.NewsViewHolder>(NewsDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItemWithCreator: NewsWithCreators) {
            with(binding) {
                newsItemTitleTextView.text = newsItemWithCreator.news.newsItem.title
                newsItemDescriptionTextView.text = newsItemWithCreator.news.newsItem.description
                newsItemDateTextView.text =
                    Utils.formatDate(newsItemWithCreator.news.newsItem.publishDate)

                setCategoryIcon(newsItemWithCreator)

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
        }

        private fun setCategoryIcon(newsItem: NewsWithCreators) {
            val iconResId = when (newsItem.news.category.getType()) {
                News.Category.Type.Advertisement -> R.raw.icon_advertisement
                News.Category.Type.Salary -> R.raw.icon_salary
                News.Category.Type.Union -> R.raw.icon_union
                News.Category.Type.Birthday -> R.raw.icon_birthday
                News.Category.Type.Holiday -> R.raw.icon_holiday
                News.Category.Type.Massage -> R.raw.icon_massage
                News.Category.Type.Gratitude -> R.raw.icon_gratitude
                News.Category.Type.Help -> R.raw.icon_help
                News.Category.Type.Unknown -> return
            }
            binding.categoryIconImageView.setImageResource(iconResId)
        }
    }
}

private object NewsDiffCallBack : DiffUtil.ItemCallback<NewsWithCreators>() {
    override fun areItemsTheSame(oldItem: NewsWithCreators, newItem: NewsWithCreators): Boolean {
        return oldItem.news.newsItem.id == newItem.news.newsItem.id
    }

    override fun areContentsTheSame(oldItem: NewsWithCreators, newItem: NewsWithCreators): Boolean {
        return oldItem == newItem
    }
}