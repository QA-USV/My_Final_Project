package ru.netology.fmhandroid.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.NoteCardItemForListBinding
import ru.netology.fmhandroid.domain.BusinessRules
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.enum.ExecutionPriority
import java.time.LocalDateTime

interface OnNoteItemClickListener {
    fun onCard(note: Note) {}
}

class NoteListAdapter(
    private val onNoteItemClickListener: OnNoteItemClickListener
) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffCallback()) {

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.bind(it)
            }
        }
    }

    class NoteViewHolder(
        private val binding: NoteCardItemForListBinding,
        private val onNoteItemClickListener: OnNoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(note: Note) {

            binding.apply {
                val executionPriority = BusinessRules.determiningPriorityLevelOfNote(
                    LocalDateTime.now(),
                    note.planeExecuteDate
                )

                when(executionPriority) {
                    ExecutionPriority.HIGH ->
                        this.noteCardItemForListPriorityIndicator.setImageResource(R.color.red)
                    ExecutionPriority.MEDIUM ->
                        this.noteCardItemForListPriorityIndicator.setImageResource(R.color.yellow)
                    ExecutionPriority.LOW ->
                        this.noteCardItemForListPriorityIndicator.setImageResource(R.color.green)
                }
                noteCardItemForListPatientName.text = note.shortPatientName
                noteCardItemForListExecutorName.text = note.shortExecutorName
                noteCardItemForListPlanDate.text = note.planeExecuteDate.toString()
                noteCardItemForListFactDate.text = note.factExecuteDate.toString()
                noteCardItemForListDescription.text = note.description
                noteCardItemForListDescription.setOnClickListener {
                    onNoteItemClickListener.onCard(note)
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
