package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemNewsControlPanelBinding
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.utils.Utils

class NewsControlPanelListAdapter : ListAdapter<News, NewsControlPanelListAdapter.NewsControlPanelViewHolder>(NewsControlPanelDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsControlPanelViewHolder {
        val binding = ItemNewsControlPanelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsControlPanelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsControlPanelViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NewsControlPanelViewHolder(
        private val binding: ItemNewsControlPanelBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: News) = binding.apply {
            newsItemTitleTextView.text = newsItem.title
            newsItemDescriptionTextView.text = newsItem.description
            newsItemPublicationDateTextView.text = newsItem.publishDate?.let { Utils.convertDate(it) }
            newsItemCreateDateTextView.text = newsItem.createDate?.let { Utils.convertDate(it) }
//            newsItemAuthorNameTextView.text = itemView.resources.getString(
//                R.string.patient_full_name_format,
//                newsItem.creator.lastName,
//                newsItem.creator.firstName.first() + ".",
//                newsItem.creator.middleName.first() + "."
//            )

            newsItem.newsCategoryId?.let { setCategoryIcon(it) }

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

            when (newsItem.publishEnabled) {
                true -> {
                    newsItemPublishedTextView.text =
                        itemView.context.getString(R.string.news_control_panel_published)
                    newsItemPublishedIconImageView.setImageResource(R.drawable.check_24)
                }
                false -> {
                    newsItemPublishedTextView.text =
                        itemView.context.getString(R.string.news_control_panel_not_published)
                    newsItemPublishedIconImageView.setImageResource(R.drawable.clear_24)
                }
            }
        }

        private fun setCategoryIcon(category: News.Category) {
            binding.categoryIconImageView.setImageResource(category.icon)
        }
    }
}

class NewsControlPanelDiffCallBack : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}
