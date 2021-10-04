package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

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

        binding.btnToNewsList.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newsListFragment)
        }

        binding.btnToAddNewsItem.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createEditNewsFragment)
        }

        binding.btnToNewsControlPanel.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newsControlPanelFragment)
        }

        binding.btnToClaimListFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_claimListFragment)
        }

        binding.btnToCreateEditClaimFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createEditClaimFragment)
        }

        return binding.root
    }
}