package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._ru_netology_fmhandroid_ui_OpenClaimFragment_GeneratedInjector
import kotlinx.coroutines.flow.collectLatest
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimListAdapter
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.databinding.FragmentListClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

@AndroidEntryPoint
class ClaimListFragment : Fragment() {

    private val viewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentListClaimBinding.inflate(inflater, container, false)


        val adapter = ClaimListAdapter(object : OnClaimItemClickListener {
            override fun onCard(claimWithCreatorAndExecutor: Claim.ClaimWithCreatorAndExecutor) {
                findNavController().navigate(R.id.action_claimListFragment_to_openClaimFragment)
            }
        })

        binding.claimListRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest { state ->
                adapter.submitList(state)
                binding.emptyClaimListText.isVisible = state.isEmpty()
            }
        }

        return binding.root
    }

}
