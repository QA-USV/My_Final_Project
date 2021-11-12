package ru.netology.fmhandroid.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.checkbox.MaterialCheckBox
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimListAdapter
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.databinding.FragmentListClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.viewmodel.ClaimCardViewModel
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

@AndroidEntryPoint
class ClaimListFragment : Fragment(R.layout.fragment_list_claim) {

    private lateinit var binding: FragmentListClaimBinding

    private val viewModel: ClaimViewModel by viewModels()
    private val claimCardViewModel: ClaimCardViewModel by viewModels()

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

        binding.apply {
            containerListClaimInclude.expandMaterialButton.visibility = View.GONE
            containerListClaimInclude.allClaimsTextView.visibility = View.GONE
            root.setBackgroundResource(R.drawable.background_app)
        }

        val adapter = ClaimListAdapter(object : OnClaimItemClickListener {
            override fun onCard(fullClaim: FullClaim) {
                fullClaim.claim.id?.let { claimCardViewModel.getAllClaimComments(it) }
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        claimCardViewModel.claimCommentsLoadedEvent.collect {
                            val action = ClaimListFragmentDirections
                                .actionClaimListFragmentToOpenClaimFragment(fullClaim)
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        })

        binding.claimListSwipeRefresh.setOnRefreshListener {
            viewModel.onRefresh()
            binding.claimListSwipeRefresh.isRefreshing = false
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.data.collectLatest { state ->
                        adapter.submitList(state)
                        binding.containerListClaimInclude.claimListRecyclerView.post {
                            binding.containerListClaimInclude.claimListRecyclerView.scrollToPosition(
                                0
                            )
                        }
                        binding.containerListClaimInclude.emptyClaimListGroup.isVisible =
                            state.isEmpty()
                    }
                }
            }
        }

        binding.containerListClaimInclude.claimListRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.data.collectLatest { state ->
                adapter.submitList(state)
                binding.containerListClaimInclude.emptyClaimListGroup.isVisible = state.isEmpty()
            }
        }

        binding.containerListClaimInclude.filtersMaterialButton.setOnClickListener {
            val view = requireActivity().layoutInflater.inflate(
                R.layout.claim_filtering_dialog,
                null
            )

            val dialog = AlertDialog.Builder(requireContext())
                .setView(view)
//                .setPositiveButton(android.R.string.ok, null)
//                .setNegativeButton(android.R.string.cancel, null)
                .create()

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.statusesFlow.collectLatest {
                    it.map { status ->
                        displayingCurrentStatusesInCheckboxes(status, view)
                    }
                }
            }

            dialog.setOnShowListener {
                val buttonOk: Button =
                    view.findViewById(R.id.claim_list_filter_ok_material_button)
                val buttonCancel: Button =
                    view.findViewById(R.id.claim_filter_cancel_material_button)
                buttonOk.setOnClickListener {
                    val checkedStatusList = mutableListOfClaimStatus(view)

                    viewModel.onFilterClaimsMenuItemClicked(checkedStatusList)

                    dialog.dismiss()
                }
                buttonCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }
            dialog.show()
            val displayRectangle = Rect()
            val window: Window = requireActivity().window

            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

            dialog.window!!.setLayout(
                (displayRectangle.width() *
                        0.8f).toInt(), (displayRectangle.height() * 0.6f).toInt()
            )
        }

        binding.containerCustomAppBarIncludeOnFragmentListClaim.mainMenuImageButton.setOnClickListener {
            mainMenu.show()
        }

        binding.containerListClaimInclude.addNewClaimMaterialButton.setOnClickListener {
            findNavController().navigate(R.id.action_claimListFragment_to_createEditClaimFragment)
        }
    }

    private fun mutableListOfClaimStatus(view: View): MutableList<Claim.Status> {
        val checkedStatusList = mutableListOf<Claim.Status>()

        if (view.findViewById<MaterialCheckBox>(R.id.item_filter_open).isChecked)
            checkedStatusList.add(Claim.Status.OPEN)
        if (view.findViewById<MaterialCheckBox>(R.id.item_filter_in_progress).isChecked)
            checkedStatusList.add(Claim.Status.IN_PROGRESS)
        if (view.findViewById<MaterialCheckBox>(R.id.item_filter_executed).isChecked)
            checkedStatusList.add(Claim.Status.EXECUTED)
        if (view.findViewById<MaterialCheckBox>(R.id.item_filter_cancelled).isChecked)
            checkedStatusList.add(Claim.Status.CANCELLED)
        return checkedStatusList
    }

    private fun displayingCurrentStatusesInCheckboxes(
        status: Claim.Status,
        view: View
    ) {
        if (status == Claim.Status.OPEN) {
            view.findViewById<MaterialCheckBox>(R.id.item_filter_open).isChecked =
                true
        }
        if (status == Claim.Status.IN_PROGRESS) {
            view.findViewById<MaterialCheckBox>(R.id.item_filter_in_progress).isChecked =
                true
        }
        if (status == Claim.Status.EXECUTED) {
            view.findViewById<MaterialCheckBox>(R.id.item_filter_executed).isChecked =
                true
        }
        if (status == Claim.Status.CANCELLED) {
            view.findViewById<MaterialCheckBox>(R.id.item_filter_cancelled).isChecked =
                true
        }
    }
}
