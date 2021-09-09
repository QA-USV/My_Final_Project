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
import ru.netology.fmhandroid.domain.BusinessRules
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.enum.ExecutionPriority
import ru.netology.fmhandroid.utils.Utils
import java.time.LocalDateTime

interface OnWishItemClickListener {
    fun onCard(wish: Wish) {}
    fun onDescription(wish: Wish) {}
}

class WishListAdapter(
    private val onWishItemClickListener: OnWishItemClickListener
) : ListAdapter<Wish, WishListAdapter.WishViewHolder>(WishDiffCallback()) {

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

        fun bind(wish: Wish) {
            binding.apply {
                prioritization(wish)
//                patientNameMaterialTextView.text = wish.shortPatientName
//                executorNameMaterialTextView.text = wish.shortExecutorName
                planTimeMaterialTextView.text = wish.planeExecuteDate?.let { Utils.convertTime(it) }
                planDateMaterialTextView.text = wish.planeExecuteDate?.let { Utils.convertDate(it) }
                descriptionMaterialTextView.text = wish.description

                descriptionMaterialTextView.setOnClickListener {
                    onWishItemClickListener.onDescription(wish)
                }
            }
        }

        private fun ItemWishBinding.prioritization(wish: Wish) {
            val executionPriority = wish.planeExecuteDate.let {
                BusinessRules.determiningPriorityLevelOfNote(
                    LocalDateTime.now(),
                    it ?: LocalDateTime.now()
                // TODO ("Продумать, что сделать если плановой даты нет или она null")
                )
            }

            when (executionPriority) {
                ExecutionPriority.HIGH ->
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

                ExecutionPriority.MEDIUM ->
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

                ExecutionPriority.LOW ->
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

    class WishDiffCallback : DiffUtil.ItemCallback<Wish>() {
        override fun areItemsTheSame(oldItem: Wish, newItem: Wish): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Wish, newItem: Wish): Boolean {
            return oldItem == newItem
        }
    }
}