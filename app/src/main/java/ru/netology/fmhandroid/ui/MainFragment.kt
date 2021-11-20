package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
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
import ru.netology.fmhandroid.adapter.NewsListAdapter
import ru.netology.fmhandroid.databinding.FragmentMainBinding
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimViewModel
import ru.netology.fmhandroid.viewmodel.NewsViewModel

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    private val claimViewModel: ClaimViewModel by viewModels()
    private val viewModelNews: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launchWhenStarted {
            claimViewModel.openClaimEvent.collectLatest {
                val action = MainFragmentDirections
                    .actionMainFragmentToOpenClaimFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        val mainMenu = PopupMenu(
            context,
            binding.containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton
        )
        mainMenu.inflate(R.menu.menu_main)
        mainMenu.menu.removeItem(R.id.menu_item_main)
        binding.containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton.setOnClickListener {
            mainMenu.show()
        }
        mainMenu.setOnMenuItemClickListener {
            when (it.itemId) {
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

        binding.containerListClaimIncludeOnFragmentMain.apply {
            filtersMaterialButton.visibility = View.GONE
            root.setBackgroundResource(R.color.white)

            addNewClaimMaterialButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_createEditClaimFragment)
            }

            expandMaterialButton.setOnClickListener {
                when (allClaimsTextView.visibility) {
                    View.GONE -> {
                        allClaimsTextView.visibility = View.VISIBLE
                        allClaimsCardsBlockConstraintLayout.visibility = View.VISIBLE
                        expandMaterialButton.setIconResource(R.drawable.expand_less_24)
                    }
                    else -> {
                        allClaimsTextView.visibility = View.GONE
                        allClaimsCardsBlockConstraintLayout.visibility = View.GONE
                        expandMaterialButton.setIconResource(R.drawable.expand_more_24)
                    }
                }
            }

            allClaimsTextView.setOnClickListener {
                if (Utils.isOnline(requireContext())) {
                    findNavController().navigate(R.id.action_mainFragment_to_claimListFragment)
                } else {
                    showErrorToast(R.string.error)
                }
            }
        }

        val claimListAdapter = ClaimListAdapter(claimViewModel)


        binding.containerListClaimIncludeOnFragmentMain.claimListRecyclerView.adapter =
            claimListAdapter
        lifecycleScope.launchWhenCreated {
            claimViewModel.data.collectLatest { state ->
                claimListAdapter.submitList(state.take(n = 6))
            }
        }

        binding.containerListNewsIncludeOnFragmentMain.apply {
            sortNewsMaterialButton.visibility = View.GONE
            filterNewsMaterialButton.visibility = View.GONE
            editNewsMaterialButton.visibility = View.GONE

            expandMaterialButton.setOnClickListener {
                when (allNewsTextView.visibility) {
                    View.GONE -> {
                        allNewsTextView.visibility = View.VISIBLE
                        allNewsCardsBlockConstraintLayout.visibility = View.VISIBLE
                        expandMaterialButton.setIconResource(R.drawable.expand_less_24)
                    }
                    else -> {
                        allNewsTextView.visibility = View.GONE
                        allNewsCardsBlockConstraintLayout.visibility = View.GONE
                        expandMaterialButton.setIconResource(R.drawable.expand_more_24)
                    }
                }
            }

            allNewsTextView.setOnClickListener {
                if (Utils.isOnline(requireContext())) {
                    findNavController().navigate(R.id.action_mainFragment_to_newsListFragment)
                } else {
                    showErrorToast(R.string.error)
                }
            }
        }

        val newsListAdapter = NewsListAdapter()
        binding.containerListNewsIncludeOnFragmentMain.newsListRecyclerView.adapter =
            newsListAdapter
        lifecycleScope.launchWhenCreated {
            viewModelNews.data.collect { state ->
                newsListAdapter.submitList(state.filter {
                    it.news.newsItem.publishEnabled
                }.take(n = 3))
            }
        }

        lifecycleScope.launch {
            binding.mainSwipeRefresh.setOnRefreshListener {
                viewModelNews.onRefresh()
                claimViewModel.onRefresh()

                lifecycleScope.launchWhenResumed {
                    claimViewModel.claimsLoadException.collect {
                        showErrorToast(R.string.error)
                    }
                }
                binding.mainSwipeRefresh.isRefreshing = false
            }

            viewModelNews.data.collectLatest { state ->
                newsListAdapter.submitList(state.filter {
                    it.news.newsItem.publishEnabled
                }.take(n = 3))
            }

            claimViewModel.data.collectLatest { state ->
                claimListAdapter.submitList(state.take(n = 6))
                binding.containerListClaimIncludeOnFragmentMain.emptyClaimListGroup.isVisible =
                    state.isEmpty()
            }
        }
    }

    private fun showErrorToast(text: Int) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}
