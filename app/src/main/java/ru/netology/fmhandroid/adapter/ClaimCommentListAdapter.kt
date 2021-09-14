package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.databinding.ItemClaimBinding
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.utils.Utils


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
        return ClaimCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClaimCommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ClaimCommentViewHolder(
        private val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(claimComment: ClaimComment) {
            binding.apply {
                commentTextView.text = claimComment.description
                commentatorNameTextView.text = claimComment.creatorId.toString()
                commentTimeTextView.text = claimComment.createDate.toString()
                editLightImageButton.setOnClickListener {
                    Toast.makeText(it.context, "This work!", Toast.LENGTH_SHORT). show()
                }
            }
        }
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