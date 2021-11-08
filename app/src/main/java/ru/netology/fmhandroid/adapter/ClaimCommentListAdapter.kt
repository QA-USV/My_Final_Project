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
    login = "User-1",
    password = "abcd",
    firstName = "Дмитрий",
    lastName = "Винокуров",
    middleName = "Владимирович",
    phoneNumber = "+79109008765",
    email = "Vinokurov@mail.ru",
    deleted = false
)

class ClaimCommentListAdapter(
    private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener
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
        return ClaimCommentViewHolder(binding, onClaimCommentItemClickListener)
    }

    override fun onBindViewHolder(holder: ClaimCommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ClaimCommentViewHolder(
        private val binding: ItemCommentBinding,
        private val onClaimCommentItemClickListener: OnClaimCommentItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(claimComment: ClaimCommentWithCreator) {
            binding.apply {
                commentDescriptionTextView.text = claimComment.claimComment.description
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
                commentDateTextView.text =
                    claimComment.claimComment.createDate?.let {Utils.showDate(it)}
                commentTimeTextView.text =
                    claimComment.claimComment.createDate?.let { Utils.showTime(it) }

                if(claimComment.creator.id != user.id) {
                    editLightImageButton.setImageResource(R.drawable.ic_pen_light)
                } else {
                    editLightImageButton.setImageResource(R.drawable.ic_pen)
                }

                editLightImageButton.setOnClickListener {
                    onClaimCommentItemClickListener.onCard(claimComment)
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
