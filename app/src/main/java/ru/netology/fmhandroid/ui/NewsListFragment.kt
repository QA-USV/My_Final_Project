package ru.netology.fmhandroid.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.NewsListAdapter
import ru.netology.fmhandroid.databinding.FragmentNewsListBinding
import ru.netology.fmhandroid.dto.NewsFilterArgs
import ru.netology.fmhandroid.utils.Utils.convertNewsCategory
import ru.netology.fmhandroid.viewmodel.NewsViewModel

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    private lateinit var binding: FragmentNewsListBinding

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsListBinding.bind(view)

        val mainMenu = PopupMenu(
            context,
            binding.containerCustomAppBarIncludeOnFragmentNewsList.mainMenuImageButton
        )
        mainMenu.inflate(R.menu.menu_main)
        mainMenu.menu.removeItem(R.id.menu_item_news)

        mainMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_main -> {
                    findNavController().navigate(R.id.action_newsListFragment_to_mainFragment)
                    true
                }
                R.id.menu_item_claims -> {
                    findNavController().navigate(R.id.action_newsListFragment_to_claimListFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

        binding.apply {
            containerListNewsInclude.allNewsTextView.visibility = View.GONE
            containerListNewsInclude.expandMaterialButton.visibility = View.GONE
        }

        val adapter = NewsListAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.data.collectLatest {
                binding.newsListSwipeRefresh.isRefreshing = false
                adapter.submitList(it)
                binding.containerListNewsInclude.emptyTextTextView.isVisible = it.isEmpty()

                binding.containerListNewsInclude.newsListRecyclerView.post {
                    binding.containerListNewsInclude.newsListRecyclerView.scrollToPosition(
                        0
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadNewsExceptionEvent.collect {
                val activity = activity ?: return@collect
                val dialog = AlertDialog.Builder(activity)
                dialog.setMessage(R.string.error)
                    .setPositiveButton(R.string.fragment_positive_button) { alertDialog, _ ->
                        alertDialog.cancel()
                    }
                    .create()
                    .show()
            }
        }

        binding.newsListSwipeRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }

        with(binding) {
            containerListNewsInclude.editNewsMaterialButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_newsListFragment_to_newsControlPanelFragment
                )
            }

            containerListNewsInclude.sortNewsMaterialButton.setOnClickListener {
                viewModel.onSortDirectionButtonClicked()
            }

            containerCustomAppBarIncludeOnFragmentNewsList.mainMenuImageButton.setOnClickListener {
                mainMenu.show()
            }

            containerListNewsInclude.filterNewsMaterialButton.setOnClickListener {
                findNavController().navigate(R.id.action_newsListFragment_to_filterNewsFragment)
            }
        }

        binding.containerListNewsInclude.newsListRecyclerView.adapter = adapter

        setFragmentResultListener("requestKey") { _, bundle ->
            val args = bundle.getParcelable<NewsFilterArgs>("filterArgs")
            viewModel.onFilterNewsClicked(
                args?.category?.let { convertNewsCategory(it) },
                args?.dates?.get(0),
                args?.dates?.get(1)
            )
        }
    }
}
