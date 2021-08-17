package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.NoteCardItemForListBinding
import ru.netology.fmhandroid.domain.BusinessRules
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.enum.ExecutionPriority
import ru.netology.fmhandroid.utils.Utils
import java.time.LocalDateTime

interface OnItemClickListener {
    fun onDescription(note: Note) {}
}

class NoteListAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteCardItemForListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NoteViewHolder(
        private val binding: NoteCardItemForListBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {

            binding.apply {
                prioritization(note)
                patientNameMaterialTextView.text = note.shortPatientName
                executorNameMaterialTextView.text = note.shortExecutorName
                planDateMaterialTextView.text = Utils.convertDate(note.planeExecuteDate!!)
                descriptionMaterialTextView.text = note.description

                descriptionMaterialTextView.setOnClickListener {
                    onItemClickListener.onDescription(note)
                }
            }
        }

        private fun NoteCardItemForListBinding.prioritization(note: Note) {

            val executionPriority = note.planeExecuteDate?.let {
                BusinessRules.determiningPriorityLevelOfNote(
                    LocalDateTime.now(),
                    it
                )
            }

            when (executionPriority) {

                ExecutionPriority.HIGH ->
                    this.noteCardItemForList.strokeColor = ContextCompat.getColor(
                        itemView.context,
                        R.color.execution_priority_medium
                    )

                ExecutionPriority.MEDIUM ->
                    this.noteCardItemForList.strokeColor = ContextCompat.getColor(
                        itemView.context,
                        R.color.execution_priority_medium
                    )

                ExecutionPriority.LOW ->
                    this.noteCardItemForList.strokeColor = ContextCompat.getColor(
                        itemView.context,
                        R.color.execution_priority_medium
                    )
            }
        }
    }

    private object NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
