package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimCommentListAdapter
import ru.netology.fmhandroid.adapter.OnCommentItemClickListener
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

@AndroidEntryPoint
class OpenClaimFragment: Fragment() {
    private lateinit var binding: FragmentOpenClaimBinding
    private val viewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_open_claim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOpenClaimBinding.bind(view)
        val args: OpenClaimFragmentArgs by navArgs()
        val claim = args.argClaim
        val adapter = ClaimCommentListAdapter(object: OnCommentItemClickListener {})

        binding.apply {
            titleTextView.text = claim.claim.title
            executorNameTextView.text =
                getString(R.string.full_name_format, claim.executor?.firstName, claim.executor?.middleName, claim.executor?.lastName)
            planeDateTextView.text = claim.claim.planExecuteDate
            statusLabelTextView.text = when(claim.claim.status) {
                Claim.Status.CANCELLED -> getString(R.string.cancel)
                Claim.Status.EXECUTED -> getString(R.string.executed)
                Claim.Status.IN_PROGRESS -> getString(R.string.in_progress)
                Claim.Status.OPEN -> getString(R.string.status_open)
                null -> "?"
            }
            descriptionTextView.text = claim.claim.description
            authorNameTextView.text =
                getString(R.string.full_name_format, claim.creator.firstName, claim.creator.middleName, claim.creator.lastName)
            createDataTextView.text = claim.claim.createDate
        }

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.claimCommentsListRecyclerView.layoutManager = mLayoutManager
        binding.claimCommentsListRecyclerView.adapter = adapter

        binding.claimCommentsListRecyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            adapter.submitList(viewModel.commentsData)
        }

    }
}