package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimListAdapter
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.databinding.FragmentListClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.utils.Events
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
    ): View {

        val binding = FragmentListClaimBinding.inflate(inflater, container, false)

        val menuFiltering = PopupMenu(context, binding.containerListClaimInclude.filtersMaterialButton)
        menuFiltering.inflate(R.menu.menu_claim_list_filtering)

        val adapter = ClaimListAdapter(object : OnClaimItemClickListener {
            override fun onCard(claimWithCreatorAndExecutor: ClaimWithCreatorAndExecutor) {
                claimWithCreatorAndExecutor.claim.id?.let { viewModel.getAllClaimComments(it) }
                claimWithCreatorAndExecutor.claim.id?.let { viewModel.getClaimById(it) }

                viewLifecycleOwner.lifecycleScope.launch {
                    Events.events.collect {
                        viewModel.claimCommentsLoadedEvent
                        val action = ClaimListFragmentDirections
                            .actionClaimListFragmentToOpenClaimFragment(claimWithCreatorAndExecutor)
                        findNavController().navigate(action)
                    }
                }
            }
        })

        menuFiltering.setOnMenuItemClickListener { menuItem ->
            claimListFiltering(menuItem, adapter, binding)
        }

        binding.containerListClaimInclude.claimListRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.dataOpenInProgress.collectLatest { state ->
                adapter.submitList(state)
                binding.containerListClaimInclude.emptyClaimListGroup.isVisible = state.isEmpty()
            }
        }

        binding.containerListClaimInclude.filtersMaterialButton.setOnClickListener {
            menuFiltering.show()
        }

        binding.containerListClaimInclude.addNewClaimMaterialButton.setOnClickListener {
            findNavController().navigate(R.id.action_claimListFragment_to_createEditClaimFragment)
        }

        return binding.root
    }

    private fun claimListFiltering(
        menuItem: MenuItem,
        adapter: ClaimListAdapter,
        binding: FragmentListClaimBinding
    ) = when (menuItem.itemId) {

        R.id.open_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.claim.status == Claim.Status.OPEN })
                    binding.containerListClaimInclude.emptyClaimListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }

        R.id.take_to_work_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.claim.status == Claim.Status.IN_PROGRESS })
                    binding.containerListClaimInclude.emptyClaimListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }

        R.id.cancel_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.claim.status == Claim.Status.CANCELLED })
                    binding.containerListClaimInclude.emptyClaimListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }

        R.id.executes_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.claim.status == Claim.Status.EXECUTED})
                    binding.containerListClaimInclude.emptyClaimListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }
        else -> false
    }
}
