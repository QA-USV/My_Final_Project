package ru.netology.fmhandroid.ui

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimListAdapter
import ru.netology.fmhandroid.adapter.NewsListAdapter
import ru.netology.fmhandroid.adapter.OnClaimItemClickListener
import ru.netology.fmhandroid.databinding.FragmentMainBinding
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.dto.NewsFilterArgs
import ru.netology.fmhandroid.dto.NewsWithCreators
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
        binding.containerCustomAppBarIncludeOnFragmentMain.mainMenuImageButton.setOnClickListener {
            mainMenu.show()
        }
        mainMenu.setOnMenuItemClickListener {
            when (it.itemId) {
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


        binding.containerListClaimIncludeOnFragmentMain.apply {
            filtersMaterialButton.visibility = View.GONE

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
                findNavController().navigate(R.id.action_mainFragment_to_claimListFragment)
            }
        }

        val claimListAdapter = ClaimListAdapter(object : OnClaimItemClickListener {
            override fun onCard(fullClaim: FullClaim) {
//                fullClaim.claim.id?.let { viewModelClaim.getAllClaimComments(it) }
//                fullClaim.claim.id?.let { viewModelClaim.getClaimById(it) }
//
//                viewLifecycleOwner.lifecycleScope.launch {
//                    Events.events.collect {
//                        viewModelClaim.claimCommentsLoadedEvent
                        val action = MainFragmentDirections
                            .actionMainFragmentToOpenClaimFragment(fullClaim)
                        findNavController().navigate(action)
//                    }
//                }
            }
        })

        binding.containerListClaimIncludeOnFragmentMain.claimListRecyclerView.adapter = claimListAdapter
        lifecycleScope.launchWhenCreated {
            viewModelClaim.dataOpenInProgress.collectLatest { state ->
                claimListAdapter.submitList(state.take( n = 6))
                binding.containerListClaimIncludeOnFragmentMain.emptyClaimListGroup.isVisible = state.isEmpty()
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
                findNavController().navigate(R.id.action_mainFragment_to_newsListFragment)
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
    }

    private suspend fun filterNews(adapter: NewsListAdapter) {
        setFragmentResultListener("requestKey") { _, bundle ->
            val args = bundle.getParcelable<NewsFilterArgs>("filterArgs")
            lifecycleScope.launch {
                if (args?.category == null && args?.dates == null) data = viewModelNews.data
                args?.category?.let { category ->
                    data = when (args.dates) {
                        null -> viewModelNews.filterNewsByCategory(
                            Utils.convertNewsCategory(
                                category
                            )
                        )
                        else -> viewModelNews.filterNewsByCategoryAndPublishDate(
                            Utils.convertNewsCategory(category), args.dates[0], args.dates[1]
                        )
                    }
                }
                if (args?.category == null) {
                    data =
                        args?.dates?.let { viewModelNews.filterNewsByPublishDate(it[0], it[1]) }
                }
                submitList(adapter, data)
            }
        }
        if (data == null) submitList(adapter, viewModelNews.data) else submitList(adapter, data)
    }

    private fun submitList(adapter: NewsListAdapter, data: Flow<List<NewsWithCreators>>?) {
        lifecycleScope.launch {
            data?.collectLatest { state ->
                adapter.submitList(state.filter {
                    it.news.newsItem.publishEnabled
                }.take(n = 3))
                binding.containerListNewsIncludeOnFragmentMain.emptyTextTextView.isVisible =
                    state.isEmpty()
            }
        }
    }
}
