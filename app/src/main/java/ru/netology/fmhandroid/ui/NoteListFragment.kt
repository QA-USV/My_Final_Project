package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.netology.fmhandroid.adapter.NoteListAdapter
import ru.netology.fmhandroid.databinding.FragmentListNoteBinding
import ru.netology.fmhandroid.viewmodel.NoteViewModel

@AndroidEntryPoint
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
        lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest { state ->
                adapter.submitList(state)
                binding.emptyNotesListText.isVisible = state.isEmpty()
            }
        }

        return binding.root
    }
}