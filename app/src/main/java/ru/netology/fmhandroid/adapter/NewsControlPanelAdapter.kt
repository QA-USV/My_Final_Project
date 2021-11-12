package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemNewsControlPanelBinding
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.utils.Utils

interface NewsOnInteractionListener {
    fun onEdit(newItemWithCreator: NewsWithCreators)
    fun onRemove(newItemWithCreator: NewsWithCreators)
}

class NewsControlPanelListAdapter(
    private val onInteractionListener: NewsOnInteractionListener
) : ListAdapter<NewsWithCreators, NewsControlPanelListAdapter.NewsControlPanelViewHolder>(
    NewsControlPanelDiffCallBack
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsControlPanelViewHolder {
        val binding = ItemNewsControlPanelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsControlPanelViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: NewsControlPanelViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    class NewsControlPanelViewHolder(
        private val binding: ItemNewsControlPanelBinding,
        private val onInteractionListener: NewsOnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItemWithCreator: NewsWithCreators) {
            with(binding) {
                newsItemTitleTextView.text = newsItemWithCreator.news.newsItem.title
                newsItemDescriptionTextView.text = newsItemWithCreator.news.newsItem.description
                newsItemPublicationDateTextView.text =
                    newsItemWithCreator.news.newsItem.publishDate?.let { Utils.formatDate(it) }
                newsItemCreateDateTextView.text =
                    newsItemWithCreator.news.newsItem.createDate?.let { Utils.formatDate(it) }
                newsItemAuthorNameTextView.text = itemView.resources.getString(
                    R.string.full_name_format,
                    newsItemWithCreator.user.lastName,
                    newsItemWithCreator.user.firstName?.first()?.plus("."),
                    newsItemWithCreator.user.middleName?.first()?.plus(".")
                )

                setCategoryIcon(newsItemWithCreator)

                newsItemMaterialCardView.setOnClickListener {
                    when (newsItemDescriptionTextView.visibility) {
                        View.GONE -> {
                            newsItemDescriptionTextView.visibility = View.VISIBLE
                            viewNewsItemImageView.setImageResource(R.drawable.expand_less_24)
                        }
                        else -> {
                            newsItemDescriptionTextView.visibility = View.GONE
                            viewNewsItemImageView.setImageResource(R.drawable.expand_more_24)
                        }
                    }
                }

                when (newsItemWithCreator.news.newsItem.publishEnabled) {
                    true -> {
                        newsItemPublishedTextView.text =
                            itemView.context.getString(R.string.news_control_panel_active)
                        newsItemPublishedIconImageView.setImageResource(R.drawable.ic_baseline_check_24)
                    }
                    false -> {
                        newsItemPublishedTextView.text =
                            itemView.context.getString(R.string.news_control_panel_not_active)
                        newsItemPublishedIconImageView.setImageResource(R.drawable.ic_baseline_clear_24)
                    }
                }

                editNewsItemImageView.setOnClickListener {
                    onInteractionListener.onEdit(newsItemWithCreator)
                }

                deleteNewsItemImageView.setOnClickListener {
                    onInteractionListener.onRemove(newsItemWithCreator)
                }
            }
        }

        private fun setCategoryIcon(newsItem: NewsWithCreators) {
            binding.categoryIconImageView.setImageResource(
                when (newsItem.news.category.id) {
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

private object NewsControlPanelDiffCallBack : DiffUtil.ItemCallback<NewsWithCreators>() {
    override fun areItemsTheSame(oldItem: NewsWithCreators, newItem: NewsWithCreators): Boolean {
        return oldItem.news.newsItem.id == newItem.news.newsItem.id
    }

    override fun areContentsTheSame(oldItem: NewsWithCreators, newItem: NewsWithCreators): Boolean {
        return oldItem == newItem
    }
}
