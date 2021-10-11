package ru.netology.fmhandroid.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimListAdapter
import ru.netology.fmhandroid.adapter.NewsListAdapter
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.databinding.FragmentMainBinding
import ru.netology.fmhandroid.dto.ClaimWithCreatorAndExecutor
import ru.netology.fmhandroid.dto.NewsFilterArgs
import ru.netology.fmhandroid.dto.NewsWithCreators
import ru.netology.fmhandroid.ui.NewsControlPanelFragment.Companion.revert
import ru.netology.fmhandroid.utils.Events
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimViewModel
import ru.netology.fmhandroid.viewmodel.NewsViewModel

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val viewModelClaim: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var data: Flow<List<NewsWithCreators>>? = null
    private val viewModelNews: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        val mainMenu = PopupMenu(context, binding.containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton)
        mainMenu.inflate(R.menu.menu_main)
        mainMenu.menu.removeItem(R.id.menu_item_main)

        mainMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_item_users -> {
                    // Дописать переход на фрагмент со списком пользователей!!!
                    true
                }
                R.id.menu_item_claims -> {
                    findNavController().navigate(R.id.action_mainFragment_to_claimListFragment)
                    true
                }
                R.id.menu_item_news -> {
                    findNavController().navigate(R.id.action_mainFragment_to_newsListFragment)
                    true
                }
                else -> false
            }
        }

        val claimListAdapter = ClaimListAdapter(object : OnClaimItemClickListener {
            override fun onCard(claimWithCreatorAndExecutor: ClaimWithCreatorAndExecutor) {
                claimWithCreatorAndExecutor.claim.id?.let { viewModelClaim.getAllClaimComments(it) }
                claimWithCreatorAndExecutor.claim.id?.let { viewModelClaim.getClaimById(it) }

                viewLifecycleOwner.lifecycleScope.launch {
                    Events.events.collect {
                        viewModelClaim.claimCommentsLoadedEvent
                        val action = MainFragmentDirections
                            .actionMainFragmentToOpenClaimFragment(claimWithCreatorAndExecutor)
                        findNavController().navigate(action)
                    }
                }
            }
        })

        binding.containerListClaimIncludeOnFragmentMain.claimListRecyclerView.adapter = claimListAdapter
        lifecycleScope.launchWhenCreated {
            viewModelClaim.dataOpenInProgress.collectLatest { state ->
                claimListAdapter.submitList(state)
                binding.containerListClaimIncludeOnFragmentMain.emptyClaimListGroup.isVisible = state.isEmpty()
            }
        }

        val newsListAdapter = NewsListAdapter()
        binding.containerListNewsIncludeOnFragmentMain.newsListRecyclerView.adapter = newsListAdapter

        lifecycleScope.launchWhenCreated {
            filterNews(newsListAdapter)
        }
// срабатывает при клике на claim card?!
//
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            Events.events.collect {
//                viewModelNews.loadNewsExceptionEvent
//                val activity = activity ?: return@collect
//                val dialog = AlertDialog.Builder(activity)
//                dialog.setMessage(R.string.error)
//                    .setPositiveButton(R.string.fragment_positive_button) { alertDialog, _ ->
//                        alertDialog.cancel()
//                    }
//                    .create()
//                    .show()
//            }
//        }

        with(binding) {
            containerListNewsIncludeOnFragmentMain.editNewsMaterialButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_newsListFragment_to_newsControlPanelFragment
                )
            }

            containerListNewsIncludeOnFragmentMain.sortNewsMaterialButton.setOnClickListener {
                if (data == null) data = viewModelNews.data
                lifecycleScope.launch {
                    if (containerListNewsIncludeOnFragmentMain.sortNewsMaterialButton.isChecked) {
                        containerListNewsIncludeOnFragmentMain.newsListRecyclerView.revert(true, requireActivity())
                        data?.collectLatest { state ->
                            newsListAdapter.submitList(state.reversed())
                        }
                    } else {
                        containerListNewsIncludeOnFragmentMain.newsListRecyclerView.revert(true, requireActivity())
                        submitList(newsListAdapter, data)
                    }
                }
            }

            containerListNewsIncludeOnFragmentMain.filterNewsMaterialButton.setOnClickListener {
                findNavController().navigate(R.id.action_newsListFragment_to_filterNewsFragment)
            }

            containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton.setOnClickListener {
                mainMenu.show()
            }
        }

        binding.containerListNewsIncludeOnFragmentMain.newsListRecyclerView.adapter = newsListAdapter
   }

    private suspend fun filterNews(adapter: NewsListAdapter) {
        setFragmentResultListener("requestKey") { _, bundle ->
            val args = bundle.getParcelable<NewsFilterArgs>("filterArgs")
            lifecycleScope.launch {
                if (args?.category == null && args?.dates == null) data = viewModelNews.data
                args?.category?.let { category ->
                    data = when (args.dates) {
                        null -> viewModelNews.filterNewsByCategory(Utils.convertNewsCategory(category))
                        else -> viewModelNews.filterNewsByCategoryAndPublishDate(
                            Utils.convertNewsCategory(category), args.dates[0], args.dates[1]
                        )
                    }
                }
                if (args?.category == null) {
                    data = args?.dates?.let { viewModelNews.filterNewsByPublishDate(it[0], it[1]) }
                }
                submitList(adapter, data)
            }
        }
        if (data == null) submitList(adapter, viewModelNews.data) else submitList(adapter, data)
    }

    private fun submitList(adapter: NewsListAdapter, data: Flow<List<NewsWithCreators>>?) {
        lifecycleScope.launch {
            data?.collectLatest { state ->
                adapter.submitList(state)
                binding.containerListNewsIncludeOnFragmentMain.emptyTextTextView.isVisible = state.isEmpty()
            }
        }
    }
}