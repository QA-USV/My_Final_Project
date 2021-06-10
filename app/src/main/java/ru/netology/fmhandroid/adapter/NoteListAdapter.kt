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
            holder.bind(it)
        }
    }

    class NoteViewHolder(
        private val binding: NoteCardItemForListBinding,
        private val onNoteItemClickListener: OnNoteItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(note: Note) {

            binding.apply {
                val executionPriority = BusinessRules.determiningPriorityLevelOfNote(LocalDateTime.now(), note.planeExecuteDate)
                when(note.executionPriority) {
                    Status.RED -> this.noteCardItemForListPriorityIndicator.setImageResource(R.color.red)
                    Status.YELLOW -> this.noteCardItemForListPriorityIndicator.setImageResource(R.color.yellow)
                    Status.GREEN -> this.noteCardItemForListPriorityIndicator.setImageResource(R.color.green)
                }
                noteCardItemForListPatientName.text = note.shortPatientName
                noteCardItemForListExecutorName.text = note.shortExecutorName
                noteCardItemForListPlanDate.text = note.planeExecuteDate
                noteCardItemForListFactDate.text = note.factExecuteDate
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
