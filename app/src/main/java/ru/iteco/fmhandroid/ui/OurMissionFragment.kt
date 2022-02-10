package ru.iteco.fmhandroid.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.adapter.ClaimCommentListAdapter
import ru.iteco.fmhandroid.adapter.OnClaimCommentItemClickListener
import ru.iteco.fmhandroid.adapter.OnOurMissionItemClickListener
import ru.iteco.fmhandroid.adapter.OurMissionItemListAdapter
import ru.iteco.fmhandroid.databinding.FragmentOurMissionBinding
import ru.iteco.fmhandroid.dto.ClaimComment
import ru.iteco.fmhandroid.ui.viewdata.OurMissionItemViewData
import ru.iteco.fmhandroid.viewmodel.OurMissionViewModel

@AndroidEntryPoint
class OurMissionFragment: Fragment(R.layout.fragment_our_mission) {
    private val viewModel: OurMissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOurMissionBinding.bind(view)
        val adapter = OurMissionItemListAdapter(object : OnOurMissionItemClickListener {
            override fun onCard(ourMissionItem: OurMissionItemViewData) {
                viewModel.onCard(ourMissionItem)
            }
        }, viewModel)

        binding.ourMissionItemListRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}