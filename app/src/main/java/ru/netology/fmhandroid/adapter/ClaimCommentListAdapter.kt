package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.AuthViewModel


interface OnClaimCommentItemClickListener {
    fun onCard(claimComment: ClaimComment)
}

class ClaimCommentListAdapter(
    private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener,
    private val authViewModel: AuthViewModel
) : ListAdapter<ClaimComment, ClaimCommentListAdapter.ClaimCommentViewHolder>(
        ClaimCommentDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimCommentViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClaimCommentViewHolder(binding, onClaimCommentItemClickListener, authViewModel)
    }

    override fun onBindViewHolder(holder: ClaimCommentViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    class ClaimCommentViewHolder(
        private val binding: ItemCommentBinding,
        private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener,
        private val authViewModel: AuthViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(claimComment: ClaimComment) {
            with(binding) {
                commentDescriptionTextView.text = claimComment.description
                commentatorNameTextView.text = claimComment.creatorName

                commentDateTextView.text =
                    Utils.formatDate(claimComment.createDate)
                commentTimeTextView.text =
                    Utils.formatTime(claimComment.createDate)

                editCommentImageButton.setImageResource(
                    if (claimComment.creatorId != authViewModel.currentUser.id) R.drawable.ic_pen_light else R.drawable.ic_pen
                )
                editCommentImageButton.setOnClickListener {
                    if (authViewModel.currentUser.id == claimComment.creatorId) {
                        onClaimCommentItemClickListener.onCard(claimComment)
                    } else {
                        return@setOnClickListener
                    }
                }
            }
        }
    }

    private object ClaimCommentDiffCallback : DiffUtil.ItemCallback<ClaimComment>() {
        override fun areItemsTheSame(
            oldItem: ClaimComment,
            newItem: ClaimComment
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ClaimComment,
            newItem: ClaimComment
        ): Boolean {
            return oldItem == newItem
        }
    }
}
