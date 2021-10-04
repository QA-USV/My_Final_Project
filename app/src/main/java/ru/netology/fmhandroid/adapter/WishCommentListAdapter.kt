package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.WishCommentWithCreator
import ru.netology.fmhandroid.utils.Utils

interface OnWishCommentItemClickListener {
    fun onCard(wishComment: WishCommentWithCreator) {}
}

class WishCommentListAdapter(
    private val onWishCommentItemClickListener: OnWishCommentItemClickListener
) :
    ListAdapter<WishCommentWithCreator, WishCommentListAdapter.WishCommentViewHolder>(
        WishCommentDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishCommentViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishCommentViewHolder(binding, onWishCommentItemClickListener)
    }

    override fun onBindViewHolder(holder: WishCommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class WishCommentViewHolder(
        private val binding: ItemCommentBinding,
        private val onWishCommentItemClickListener: OnWishCommentItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wishComment: WishCommentWithCreator) {
            binding.apply {
                commentDescriptionTextView.text = wishComment.wishComment.description
                commentatorNameTextView.text = wishComment.creator.lastName?.let { lastName ->
                    wishComment.creator.firstName?.let { firstName ->
                        wishComment.creator.middleName?.let { middleName ->
                            Utils.shortUserNameGenerator(
                                lastName,
                                firstName,
                                middleName
                            )
                        }
                    }
                }
                commentTimeTextView.text =
                    wishComment.wishComment.createDate?.let { Utils.showDateTimeInOne(it) }

                editLightImageButton.setOnClickListener {
                    onWishCommentItemClickListener.onCard(wishComment)
                }
            }
        }
    }

    class WishCommentDiffCallback : DiffUtil.ItemCallback<WishCommentWithCreator>() {
        override fun areItemsTheSame(
            oldItem: WishCommentWithCreator,
            newItem: WishCommentWithCreator
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WishCommentWithCreator,
            newItem: WishCommentWithCreator
        ): Boolean {
            return oldItem == newItem
        }
    }
}
