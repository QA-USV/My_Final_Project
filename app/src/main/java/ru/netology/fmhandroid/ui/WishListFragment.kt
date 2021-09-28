package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

        val adapter = WishListAdapter(object : OnWishItemClickListener {
            override fun onDescription(wishWithAllUsers: WishWithAllUsers) {
                val activity = activity ?: return
                val dialog = activity.let { activity ->
                    AlertDialog.Builder(activity)
                }
                dialog.setMessage(wishWithAllUsers.wish.title)
                    .setPositiveButton(R.string.close) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
        })

        binding.wishListRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.dataOpenInProgress.collectLatest { state ->
                adapter.submitList(state)
                binding.emptyWishListGroup.isVisible = state.isEmpty()
            }
        }

        return binding.root
    }
}