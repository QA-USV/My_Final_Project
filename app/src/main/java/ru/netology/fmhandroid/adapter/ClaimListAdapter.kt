package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.databinding.ItemClaimBinding
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.utils.Utils

interface OnClaimItemClickListener {
    fun onCard(claimWithCreatorAndExecutor: ClaimWithCreatorAndExecutor) {}
    fun onDescription(claimWithCreatorAndExecutor: ClaimWithCreatorAndExecutor) {}
}

class ClaimListAdapter(
    private val onClaimItemClickListener: OnClaimItemClickListener
) : ListAdapter<ClaimWithCreatorAndExecutor, ClaimListAdapter.ClaimViewHolder>(
    ClaimDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimViewHolder {
        val binding = ItemClaimBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClaimViewHolder(binding, onClaimItemClickListener)
    }

    override fun onBindViewHolder(holder: ClaimViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ClaimViewHolder(
        private val binding: ItemClaimBinding,
        private val onClaimItemClickListener: OnClaimItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(claimWithCreatorAndExecutor: ClaimWithCreatorAndExecutor) {
            binding.apply {
                executorNameMaterialTextView.text = Utils.shortUserNameGenerator(
                    claimWithCreatorAndExecutor.executor.firstName.toString(),
                    claimWithCreatorAndExecutor.executor.lastName.toString(),
                    claimWithCreatorAndExecutor.executor.middleName.toString()
                )
                planTimeMaterialTextView.text = Utils.convertTime(
                    claimWithCreatorAndExecutor.claim.planExecuteDate.toString()
                )
                planDateMaterialTextView.text = Utils.convertDate(claimWithCreatorAndExecutor
                    .claim.planExecuteDate.toString())
                descriptionMaterialTextView.text = claimWithCreatorAndExecutor.claim.description

                claimListCard.setOnClickListener {
                    onClaimItemClickListener.onCard(claimWithCreatorAndExecutor)
                }
            }
        }
    }

    class ClaimDiffCallback : DiffUtil.ItemCallback<ClaimWithCreatorAndExecutor>() {
        override fun areItemsTheSame(
            oldItem: ClaimWithCreatorAndExecutor,
            newItem: ClaimWithCreatorAndExecutor
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ClaimWithCreatorAndExecutor,
            newItem: ClaimWithCreatorAndExecutor
        ): Boolean {
            return oldItem == newItem
        }
    }

}