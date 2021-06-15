package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.fmhandroid.adapter.NoteListAdapter
import ru.netology.fmhandroid.adapter.OnNoteItemClickListener
import ru.netology.fmhandroid.databinding.FragmentListNotesBinding
import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.viewmodel.NoteViewModel

class NotesListFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentListNotesBinding.inflate(inflater, container, false)


        val adapter = NoteListAdapter(object : OnNoteItemClickListener {
            override fun onNote(note: Note) {
                TODO()
            }
        })

        binding.rvNotesList.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner
        ) { state ->
            adapter.submitList(state.notes)
            binding.emptyNotesListText.isVisible = state.empty
        }

        return binding.root
    }

}