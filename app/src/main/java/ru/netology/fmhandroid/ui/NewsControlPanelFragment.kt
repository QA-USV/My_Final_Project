package ru.netology.fmhandroid.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.NewsControlPanelListAdapter
import ru.netology.fmhandroid.adapter.NewsOnInteractionListener
import ru.netology.fmhandroid.databinding.FragmentNewsControlPanelBinding
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsFilterArgs
import ru.netology.fmhandroid.dto.NewsWithCategory
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.NewsControlPanelViewModel

@AndroidEntryPoint
class NewsControlPanelFragment : Fragment(R.layout.fragment_news_control_panel) {
    private lateinit var binding: FragmentNewsControlPanelBinding
    private val viewModel: NewsControlPanelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launchWhenCreated {
            viewModel.onRefresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsControlPanelBinding.bind(view)

        val mainMenu = PopupMenu(
            context,
            binding.containerCustomAppBarIncludeOnFragmentNewsControlPanel.mainMenuImageButton
        )
        mainMenu.inflate(R.menu.menu_main)
        binding.containerCustomAppBarIncludeOnFragmentNewsControlPanel
            .mainMenuImageButton.setOnClickListener {
                mainMenu.show()
            }
        mainMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_main -> {
                    findNavController().navigate(R.id.action_newsControlPanelFragment_to_mainFragment)
                    true
                }
                R.id.menu_item_claims -> {
                    findNavController().navigate(R.id.action_newsControlPanelFragment_to_claimListFragment)
                    true
                }
                R.id.menu_item_news -> {
                    findNavController().navigate(R.id.action_newsControlPanelFragment_to_newsListFragment)
                    true
                }
                else -> false
            }
        }

        val activity = activity ?: return
        val dialog = AlertDialog.Builder(activity)

        val adapter = NewsControlPanelListAdapter(object : NewsOnInteractionListener {
            override fun onCard(newsItem: News) {
                viewModel.onCard(newsItem)
            }

            override fun onEdit(newItemWithCategory: NewsWithCategory) {
                val action = NewsControlPanelFragmentDirections
                    .actionNewsControlPanelFragmentToCreateEditNewsFragment(newItemWithCategory)
                findNavController().navigate(action)
            }

            override fun onRemove(newItemWithCategory: NewsWithCategory) {
                dialog.setMessage(R.string.irrevocable_deletion)
                    .setPositiveButton(R.string.fragment_positive_button) { alertDialog, _ ->
                        newItemWithCategory.newsItem.id?.let { viewModel.remove(it) }
                        alertDialog.cancel()
                    }
                    .setNegativeButton(R.string.cancel) { alertDialog, _ ->
                        alertDialog.cancel()
                    }
                    .create()
                    .show()
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest { state ->
                    adapter.submitList(state)

                    binding.errorLoadingGroup.isVisible =
                        state.isEmpty()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadNewsExceptionEvent.collect {
                dialog.setMessage(R.string.error)
                    .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.removeNewsItemExceptionEvent.collect {
                dialog.setMessage(R.string.error_removing)
                    .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
        }

        with(binding) {
            sortNewsMaterialButton.setOnClickListener {
                viewModel.onSortDirectionButtonClicked()
                binding.newsListRecyclerView.post {
                    binding.newsListRecyclerView.scrollToPosition(
                        0
                    )
                }
            }

            addNewsImageView.setOnClickListener {
                findNavController().navigate(
                    R.id.action_newsControlPanelFragment_to_createEditNewsFragment
                )
            }

            filterNewsMaterialButton.setOnClickListener {
                findNavController().navigate(R.id.action_newsControlPanelFragment_to_filterNewsFragment)
            }
        }

        binding.newsListRecyclerView.adapter = adapter

        binding.newsControlPanelSwipeToRefresh.setOnRefreshListener {
            viewModel.onRefresh()
            binding.newsControlPanelSwipeToRefresh.isRefreshing = false
        }

        setFragmentResultListener("requestKey") { _, bundle ->
            val args = bundle.getParcelable<NewsFilterArgs>("filterArgs")
            viewModel.onFilterNewsClicked(
                args?.category?.let { Utils.convertNewsCategory(it) },
                args?.dates?.get(0),
                args?.dates?.get(1)
            )
        }
    }
}
