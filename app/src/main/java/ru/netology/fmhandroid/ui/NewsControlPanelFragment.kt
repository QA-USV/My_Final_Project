package ru.netology.fmhandroid.ui

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.NewsControlPanelListAdapter
import ru.netology.fmhandroid.adapter.NewsOnInteractionListener
import ru.netology.fmhandroid.databinding.FragmentNewsControlPanelBinding
import ru.netology.fmhandroid.dto.NewsFilterArgs
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.utils.Events
import ru.netology.fmhandroid.utils.Utils.convertNewsCategory
import ru.netology.fmhandroid.viewmodel.NewsViewModel

@AndroidEntryPoint
class NewsControlPanelFragment : Fragment(R.layout.fragment_news_control_panel) {
    private lateinit var binding: FragmentNewsControlPanelBinding
    private val viewModel: NewsViewModel by viewModels()
    private var data: Flow<List<NewsWithCreators>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsControlPanelBinding.bind(view)

        val adapter = NewsControlPanelListAdapter(object : NewsOnInteractionListener {
            override fun onEdit(newItemWithCreator: NewsWithCreators) {
                val action = NewsControlPanelFragmentDirections
                    .actionNewsControlPanelFragmentToCreateEditNewsFragment(newItemWithCreator)
                findNavController().navigate(action)
            }

            override fun onRemove(newItemWithCreator: NewsWithCreators) {
                newItemWithCreator.news.newsItem.id?.let { viewModel.remove(it) }
            }
        })

        lifecycleScope.launchWhenCreated {
            filterNews(adapter)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            Events.events.collect {
                val activity = activity ?: return@collect
                val dialog = AlertDialog.Builder(activity)
                when (it) {
                    viewModel.loadNewsExceptionEvent ->
                        dialog.setMessage(R.string.error)
                            .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                                dialog.cancel()
                            }
                            .create()
                            .show()

                    viewModel.removeNewsItemExceptionEvent ->
                        dialog.setMessage(R.string.error_removing)
                            .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                                dialog.cancel()
                            }
                            .create()
                            .show()
                }
            }
        }

        with(binding) {
            sortNewsMaterialButton.setOnClickListener {
                if (data == null) data = viewModel.data
                lifecycleScope.launch {
                    if (binding.sortNewsMaterialButton.isChecked) {
                        newsListRecyclerView.revert(true, requireActivity())
                        data?.collectLatest { state ->
                            adapter.submitList(state.reversed())
                        }
                    } else {
                        newsListRecyclerView.revert(true, requireActivity())
                        submitList(adapter, data)
                    }
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
    }

    private suspend fun filterNews(adapter: NewsControlPanelListAdapter) {
        setFragmentResultListener("requestKey") { _, bundle ->
            val args = bundle.getParcelable<NewsFilterArgs>("filterArgs")
            lifecycleScope.launch {
                if (args?.category == null && args?.dates == null) data = viewModel.data
                args?.category?.let { category ->
                    data = when (args.dates) {
                        null -> viewModel.filterNewsByCategory(convertNewsCategory(category))
                        else -> viewModel.filterNewsByCategoryAndPublishDate(
                            convertNewsCategory(category), args.dates[0], args.dates[1]
                        )
                    }
                }
                if (args?.category == null) {
                    data = args?.dates?.let { viewModel.filterNewsByPublishDate(it[0], it[1]) }
                }
                submitList(adapter, data)
            }
        }
        if (data == null) submitList(adapter, viewModel.data) else submitList(adapter, data)
    }

    private fun submitList(
        adapter: NewsControlPanelListAdapter,
        data: Flow<List<NewsWithCreators>>?
    ) {
        lifecycleScope.launch {
            data?.collectLatest { state ->
                adapter.submitList(state)
                binding.emptyTextTextView.isVisible = state.isEmpty()
            }
        }
    }

    companion object {
        fun RecyclerView.revert(stackFromEnd: Boolean, requireActivity: Activity) {
            val linearLayoutManager = LinearLayoutManager(requireActivity)
            linearLayoutManager.stackFromEnd = stackFromEnd
            layoutManager = linearLayoutManager
        }
    }
}