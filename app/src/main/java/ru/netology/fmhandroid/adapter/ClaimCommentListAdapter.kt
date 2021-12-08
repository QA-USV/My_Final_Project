package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemCommentBinding
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils


interface OnClaimCommentItemClickListener {
    fun onCard(claimComment: ClaimCommentWithCreator)
}

// Временная переменная. После авторизации заменить на залогиненного юзера
val user = User(
    id = 1,
    admin = false,
    firstName = "Дмитрий",
    lastName = "Винокуров",
    middleName = "Владимирович",
)

class ClaimCommentListAdapter(
    private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener
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
        return ClaimCommentViewHolder(binding, onClaimCommentItemClickListener)
    }

    override fun onBindViewHolder(holder: ClaimCommentViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
        }
    }

    class ClaimCommentViewHolder(
        private val binding: ItemCommentBinding,
        private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener
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
                    if (claimComment.creator.id != user.id) R.drawable.ic_pen_light else R.drawable.ic_pen
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
