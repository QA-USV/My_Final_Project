package ru.netology.fmhandroid.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ItemWishBinding
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.dto.WishWithAllUsers
import ru.netology.fmhandroid.utils.Utils

interface OnWishItemClickListener {
    fun onCard(wishWithAllUsers: WishWithAllUsers) {}
    fun onDescription(wishWithAllUsers: WishWithAllUsers) {}
}

class WishListAdapter(
    private val onWishItemClickListener: OnWishItemClickListener
) : ListAdapter<WishWithAllUsers, WishListAdapter.WishViewHolder>(WishDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        val binding = ItemWishBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishViewHolder(binding, onWishItemClickListener)
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class WishViewHolder(
        private val binding: ItemWishBinding,
        private val onWishItemClickListener: OnWishItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wishWithAllUsers: WishWithAllUsers) {
            binding.apply {
                prioritization(wishWithAllUsers)
                patientNameMaterialTextView.text = Utils.shortUserNameGenerator(
                    wishWithAllUsers.patient.lastName.toString(),
                    wishWithAllUsers.patient.firstName.toString(),
                    wishWithAllUsers.patient.middleName.toString()
                )
                if (wishWithAllUsers.executor == null) {
                    executorNameMaterialTextView.setText(R.string.not_assigned)
                } else {
                    executorNameMaterialTextView.text =
                    Utils.shortUserNameGenerator(
                        wishWithAllUsers.executor.lastName.toString(),
                        wishWithAllUsers.executor.firstName.toString(),
                        wishWithAllUsers.executor.middleName.toString()
                    )
                }
                planTimeMaterialTextView.text = wishWithAllUsers.wish.planExecuteDate?.let { Utils.showTime(it) }
                planDateMaterialTextView.text = wishWithAllUsers.wish.planExecuteDate?.let { Utils.showDate(it) }
                themeMaterialTextView.text = wishWithAllUsers.wish.title

                themeMaterialTextView.setOnClickListener {
                    onWishItemClickListener.onDescription(wishWithAllUsers)
                }

                itemWish.setOnClickListener {
                    onWishItemClickListener.onCard(wishWithAllUsers)
                }
            }
        }

        private fun ItemWishBinding.prioritization(wishWithAllUsers: WishWithAllUsers) {
//            val executionPriority = wishWithAllUsers.wish.planExecuteDate.let {
//                BusinessRules.determiningPriorityLevelOfWish(
//                    LocalDateTime.now(),
//                    Utils.fromLongToLocalDateTime(it!!)
//                )
//            }

            when (wishWithAllUsers.wish.priority) {
                Wish.Priority.HIGH ->
                    itemWish.apply {
                        this.strokeColor = ContextCompat.getColor(
                            itemView.context,
                            R.color.execution_priority_high
                        )
                        greenTriangleArrowImageView.setImageResource(R.drawable.ic_red_triangle_arrow)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            outlineSpotShadowColor = ContextCompat.getColor(
                                itemView.context,
                                R.color.execution_priority_high
                            )
                        }
                    }

                Wish.Priority.MEDIUM ->
                    this.itemWish.apply {
                        strokeColor = ContextCompat.getColor(
                            itemView.context,
                            R.color.execution_priority_medium
                        )
                        greenTriangleArrowImageView.setImageResource(R.drawable.ic_yellow_triangle_arrow)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            outlineSpotShadowColor = ContextCompat.getColor(
                                itemView.context,
                                R.color.execution_priority_medium
                            )
                        }
                    }

                Wish.Priority.LOW ->
                    this.itemWish.apply {
                        strokeColor = ContextCompat.getColor(
                            itemView.context,
                            R.color.execution_priority_low
                        )
                        greenTriangleArrowImageView.setImageResource(R.drawable.ic_green_triangle_arrow)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            outlineSpotShadowColor = ContextCompat.getColor(
                                itemView.context,
                                R.color.execution_priority_low
                            )
                        }
                    }
            }
        }
    }

    class WishDiffCallback : DiffUtil.ItemCallback<WishWithAllUsers>() {
        override fun areItemsTheSame(oldItem: WishWithAllUsers, newItem: WishWithAllUsers): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WishWithAllUsers, newItem: WishWithAllUsers): Boolean {
            return oldItem == newItem
        }
    }
}