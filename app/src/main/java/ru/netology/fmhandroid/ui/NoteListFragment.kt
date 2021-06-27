package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.fmhandroid.adapter.NoteListAdapter
import ru.netology.fmhandroid.databinding.FragmentListNoteBinding
import ru.netology.fmhandroid.viewmodel.NoteViewModel

class NoteListFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentListNoteBinding.inflate(inflater, container, false)


        val adapter = NoteListAdapter()

        binding.notesListRecyclerView.adapter = adapter
        viewModel.data.observe(
            viewLifecycleOwner
        ) { state ->
            adapter.submitList(state.notes)
            binding.emptyNotesListText.isVisible = state.notes.isEmpty()
        }

        return binding.root
    }
}