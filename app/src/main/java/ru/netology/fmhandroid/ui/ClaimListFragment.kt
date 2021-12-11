package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimListAdapter
import ru.netology.fmhandroid.databinding.FragmentListClaimBinding
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

@AndroidEntryPoint
class ClaimListFragment : Fragment(R.layout.fragment_list_claim) {

    private lateinit var binding: FragmentListClaimBinding
    private val viewModel: ClaimViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.claimsLoadException.collect {
                    Toast.makeText(
                        requireContext(),
                        R.string.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.openClaimEvent.collectLatest {
                val action = ClaimListFragmentDirections
                    .actionClaimListFragmentToOpenClaimFragment(it)
                findNavController().navigate(action)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.claimCommentsLoadExceptionEvent.collectLatest {
                Toast.makeText(
                    requireContext(),
                    R.string.claim_comments_load_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListClaimBinding.bind(view)

        val mainMenu = PopupMenu(
            context,
            binding.containerCustomAppBarIncludeOnFragmentListClaim.mainMenuImageButton
        )
        mainMenu.inflate(R.menu.menu_main)
        mainMenu.menu.removeItem(R.id.menu_item_claims)

        mainMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_main -> {
                    findNavController().navigate(R.id.action_claimListFragment_to_mainFragment)
                    true
                }
                R.id.menu_item_news -> {
                    findNavController().navigate(R.id.action_claimListFragment_to_newsListFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

        val adapter = ClaimListAdapter(viewModel)

        binding.claimListSwipeRefresh.setOnRefreshListener {
            viewModel.onRefresh()
            binding.claimListSwipeRefresh.isRefreshing = false
        }

        binding.containerListClaimInclude.claimListRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.data.collectLatest { state ->
                adapter.submitList(state)

                delay(200)
                binding.containerListClaimInclude.claimListRecyclerView.smoothScrollToPosition(0)

                if (state.isEmpty()) {
                    binding.containerListClaimInclude.emptyClaimListGroup.isVisible = true
                    binding.containerListClaimInclude.claimRetryMaterialButton.setOnClickListener {
                        binding.claimListSwipeRefresh.isRefreshing = true
                        viewModel.onRefresh()
                        binding.claimListSwipeRefresh.isRefreshing = false
                    }
                }
            }
        }

        binding.containerListClaimInclude.filtersMaterialButton.setOnClickListener {
            val dialog = ClaimListFilteringDialogFragment()
            dialog.show(childFragmentManager, "custom")
        }

        binding.containerCustomAppBarIncludeOnFragmentListClaim.mainMenuImageButton.setOnClickListener {
            mainMenu.show()
        }

        binding.containerListClaimInclude.addNewClaimMaterialButton.setOnClickListener {
            findNavController().navigate(R.id.action_claimListFragment_to_createEditClaimFragment)
        }
    }
}
