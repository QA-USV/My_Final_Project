package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.databinding.ItemClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.utils.Utils

interface OnClaimItemClickListener {
    fun onCard(claimWithCreatorAndExecutor: Claim.ClaimWithCreatorAndExecutor) {}
}

class ClaimListAdapter(
    private val onClaimItemClickListener: OnClaimItemClickListener
) : ListAdapter<Claim.ClaimWithCreatorAndExecutor, ClaimListAdapter.ClaimViewHolder>(
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

        fun bind(claimWithCreatorAndExecutor: Claim.ClaimWithCreatorAndExecutor) {
            binding.apply {
                executorNameMaterialTextView.text = Utils.shortUserNameGenerator(
                    claimWithCreatorAndExecutor.executor.firstName.toString(),
                    claimWithCreatorAndExecutor.executor.lastName.toString(),
                    claimWithCreatorAndExecutor.executor.middleName.toString()
                )
                planTimeMaterialTextView.text = claimWithCreatorAndExecutor
                    .claim.planExecuteDate?.let {
                        Utils.convertTime(
                            it
                        )
                    }
                planDateMaterialTextView.text = claimWithCreatorAndExecutor
                    .claim.planExecuteDate?.let {
                        Utils.convertDate(
                            it
                        )
                    }
                descriptionMaterialTextView.text = claimWithCreatorAndExecutor.claim.description

                claimListCard.setOnClickListener {
                    onClaimItemClickListener.onCard(claimWithCreatorAndExecutor)
                }
            }
        }
    }

    class ClaimDiffCallback : DiffUtil.ItemCallback<Claim.ClaimWithCreatorAndExecutor>() {
        override fun areItemsTheSame(
            oldItem: Claim.ClaimWithCreatorAndExecutor,
            newItem: Claim.ClaimWithCreatorAndExecutor
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Claim.ClaimWithCreatorAndExecutor,
            newItem: Claim.ClaimWithCreatorAndExecutor
        ): Boolean {
            return oldItem == newItem
        }
    }

}