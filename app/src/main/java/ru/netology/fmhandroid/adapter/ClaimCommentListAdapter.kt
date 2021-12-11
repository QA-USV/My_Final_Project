package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.AuthViewModel


interface OnClaimCommentItemClickListener {
    fun onCard(claimComment: ClaimCommentWithCreator)
}

class ClaimCommentListAdapter(
    private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener,
    private val authViewModel: AuthViewModel
) :
    ListAdapter<ClaimCommentWithCreator, ClaimCommentListAdapter.ClaimCommentViewHolder>(
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

        fun bind(claimComment: ClaimCommentWithCreator) {
            with(binding) {
                commentDescriptionTextView.text = claimComment.claimComment.description
                commentatorNameTextView.text =
                    Utils.generateShortUserName(
                        claimComment.creator.lastName,
                        claimComment.creator.firstName,
                        claimComment.creator.middleName
                    )

                commentDateTextView.text =
                    Utils.formatDate(claimComment.claimComment.createDate)
                commentTimeTextView.text =
                    Utils.formatTime(claimComment.claimComment.createDate)

                editCommentImageButton.setImageResource(
                    if (claimComment.creator.id != authViewModel.currentUser.id) R.drawable.ic_pen_light else R.drawable.ic_pen
                )
                editCommentImageButton.setOnClickListener {
                    onClaimCommentItemClickListener.onCard(claimComment)
                }
            }
        }
    }

    private object ClaimCommentDiffCallback : DiffUtil.ItemCallback<ClaimCommentWithCreator>() {
        override fun areItemsTheSame(
            oldItem: ClaimCommentWithCreator,
            newItem: ClaimCommentWithCreator
        ): Boolean {
            return oldItem.claimComment.id == newItem.claimComment.id
        }

        override fun areContentsTheSame(
            oldItem: ClaimCommentWithCreator,
            newItem: ClaimCommentWithCreator
        ): Boolean {
            return oldItem == newItem
        }
    }
}
