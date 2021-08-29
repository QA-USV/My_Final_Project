package ru.netology.fmhandroid.adapter

import android.os.Build
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

interface OnNoteItemClickListener {
    fun onCard(note: Note) {}
    fun onDescription(note: Note) {}
}

class NoteAdapter(
    private val onNoteItemClickListener: OnNoteItemClickListener
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteCardItemForListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding, onNoteItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NoteViewHolder(
        private val binding: NoteCardItemForListBinding,
        private val onNoteItemClickListener: OnNoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                prioritization(note)
                patientNameMaterialTextView.text = note.shortPatientName
                executorNameMaterialTextView.text = note.shortExecutorName
                planTimeMaterialTextView.text = note.planeExecuteDate?.let { Utils.convertTime(it) }
                planDateMaterialTextView.text = note.planeExecuteDate?.let { Utils.convertDate(it) }
                descriptionMaterialTextView.text = note.description

                descriptionMaterialTextView.setOnClickListener {
                    onNoteItemClickListener.onDescription(note)
                }
            }
        }

        private fun NoteCardItemForListBinding.prioritization(note: Note) {

            val executionPriority = note.planeExecuteDate.let {
                BusinessRules.determiningPriorityLevelOfNote(
                    LocalDateTime.now(),
                    it ?: LocalDateTime.now()
                // TODO ("Продумать, что сделать если плановой даты нет или она null")
                )
            }

            when (executionPriority) {

                ExecutionPriority.HIGH ->
                    this.noteCardItemForList.apply {
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
                    this.noteCardItemForList.apply {
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
                    this.noteCardItemForList.apply {
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

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
