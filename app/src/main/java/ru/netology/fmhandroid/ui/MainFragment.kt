package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentMainBinding
import ru.netology.fmhandroid.viewmodel.WishViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val wishViewModel: WishViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )

        binding.toAddPatientFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addPatientFragment)
        }

        binding.toPatientListFragment.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_patientsListFragment)
        }

        binding.btnToAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addNoteFragment)
        }

        binding.btnToNoteCard.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_noteCardFragment)
        }
        binding.toListOfNotesFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_fragment_list_notes)
        }

        return binding.root
    }
}