package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.utils.Utils


interface OnCommentItemClickListener {
    fun onCard(claimComment: ClaimCommentWithCreator) {}
}

class ClaimCommentListAdapter(
    private val onCommentItemClickListener: OnCommentItemClickListener
) :
    ListAdapter<ClaimCommentWithCreator, ClaimCommentListAdapter.ClaimCommentViewHolder>(
        ClaimCommentDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimCommentViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClaimCommentViewHolder(binding, onCommentItemClickListener)
    }

    override fun onBindViewHolder(holder: ClaimCommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ClaimCommentViewHolder(
        private val binding: ItemCommentBinding,
        private val onCommentItemClickListener: OnCommentItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(claimComment: ClaimCommentWithCreator) {
            binding.apply {
                commentTextView.text = claimComment.claimComment.description
                commentatorNameTextView.text = claimComment.creator.lastName?.let { lastName ->
                    claimComment.creator.firstName?.let { firstName ->
                        claimComment.creator.middleName?.let { middleName ->
                            Utils.shortUserNameGenerator(
                                lastName,
                                firstName,
                                middleName
                            )
                        }
                    }
                }
                commentTimeTextView.text =
                    claimComment.claimComment.createDate?.let { Utils.showDateTimeInOne(it) }

                editLightImageButton.setOnClickListener {
                    onCommentItemClickListener.onCard(claimComment)
                }
            }
        }
    }

    class ClaimCommentDiffCallback : DiffUtil.ItemCallback<ClaimCommentWithCreator>() {
        override fun areItemsTheSame(
            oldItem: ClaimCommentWithCreator,
            newItem: ClaimCommentWithCreator
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClaimCommentWithCreator,
            newItem: ClaimCommentWithCreator
        ): Boolean {
            return oldItem == newItem
        }
    }
}