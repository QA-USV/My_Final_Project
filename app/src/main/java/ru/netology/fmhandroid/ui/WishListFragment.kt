package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.OnWishItemClickListener
import ru.netology.fmhandroid.adapter.WishListAdapter
import ru.netology.fmhandroid.databinding.FragmentListWishBinding
import ru.netology.fmhandroid.dto.Wish
import ru.netology.fmhandroid.dto.WishWithAllUsers
import ru.netology.fmhandroid.viewmodel.WishViewModel

@AndroidEntryPoint
class WishListFragment : Fragment() {
    private val viewModel: WishViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentListWishBinding.inflate(inflater, container, false)

        val menuFiltering = PopupMenu(context, binding.filtersImageButton)
        menuFiltering.inflate(R.menu.menu_wish_claim_list_filtering)

        val adapter = WishListAdapter(object : OnWishItemClickListener {
            override fun onDescription(wishWithAllUsers: WishWithAllUsers) {
                val activity = activity ?: return
                val dialog = activity.let { fragment ->
                    AlertDialog.Builder(fragment)
                }
                dialog.setMessage(wishWithAllUsers.wish.title)
                    .setPositiveButton(R.string.close) { dialogFragment, _ ->
                        dialogFragment.cancel()
                    }
                    .create()
                    .show()
            }

            override fun onCard(wishWithAllUsers: WishWithAllUsers) {
                val action = WishListFragmentDirections.actionFragmentListWishesToOpenWishFragment(
                    wishWithAllUsers
                )
                findNavController().navigate(action)
            }
        })

        menuFiltering.setOnMenuItemClickListener { menuItem ->
            wishListFiltering(menuItem, adapter, binding)
        }

        binding.wishListRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.dataOpenInProgress.collectLatest { state ->
                adapter.submitList(state)
                binding.emptyWishListGroup.isVisible = state.isEmpty()
            }
        }

        binding.filtersImageButton.setOnClickListener {
            menuFiltering.show()
        }

        binding.addNewWishImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_list_wishes_to_createEditWishFragment)
        }

        return binding.root
    }

    private fun wishListFiltering(
        menuItem: MenuItem,
        adapter: WishListAdapter,
        binding: FragmentListWishBinding
    ) = when (menuItem.itemId) {

        R.id.open_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.wish.status == Wish.Status.OPEN }
                        .sortedBy {
                            it.wish.priority
                        })
                    binding.emptyWishListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }

        R.id.take_to_work_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.wish.status == Wish.Status.IN_PROGRESS }
                        .sortedBy {
                            it.wish.priority
                        })
                    binding.emptyWishListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }

        R.id.cancel_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.wish.status == Wish.Status.CANCELLED }
                        .sortedBy {
                            it.wish.priority
                        })
                    binding.emptyWishListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }

        R.id.executes_list_item -> {
            lifecycleScope.launchWhenCreated {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state.filter { it.wish.status == Wish.Status.EXECUTED }
                        .sortedBy {
                            it.wish.priority
                        })
                    binding.emptyWishListGroup.isVisible = state.isEmpty()
                }
            }
            true
        }
        else -> false
    }
}