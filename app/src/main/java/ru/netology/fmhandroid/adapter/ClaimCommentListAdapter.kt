package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.ClaimComment


interface OnCommentItemClickListener {
    fun onCard(claimComment: ClaimComment) {}
}

class ClaimCommentListAdapter(
    private val onCommentItemClickListener: OnCommentItemClickListener
) :
    ListAdapter<ClaimComment, ClaimCommentListAdapter.ClaimCommentViewHolder>(
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

        fun bind(claimComment: ClaimComment) {
            binding.apply {
                commentTextView.text = claimComment.description
                commentatorNameTextView.text = claimComment.creatorId.toString()
                commentTimeTextView.text = claimComment.createDate.toString()

                editLightImageButton.setOnClickListener {
                    onCommentItemClickListener.onCard(claimComment)
                }
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<ClaimComment>,
        currentList: MutableList<ClaimComment>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }

    class ClaimCommentDiffCallback : DiffUtil.ItemCallback<ClaimComment>() {
        override fun areItemsTheSame(
            oldItem: ClaimComment,
            newItem: ClaimComment
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClaimComment,
            newItem: ClaimComment
        ): Boolean {
            return oldItem == newItem
        }
    }
}