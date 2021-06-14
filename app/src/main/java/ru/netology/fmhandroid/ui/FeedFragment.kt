package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        binding.toAddPAtientFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addPatientFragment)
        }

        binding.toPatientListFragment.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_patientsListFragment)
        }

        binding.toAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_addNoteFragment)
        }

        return binding.root
    }
}