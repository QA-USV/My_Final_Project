package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimCommentListAdapter
import ru.netology.fmhandroid.adapter.OnCommentItemClickListener
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

@AndroidEntryPoint
class OpenClaimFragment : Fragment() {
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
        val adapter = ClaimCommentListAdapter(object : OnCommentItemClickListener {
            override fun onCard(claimComment: ClaimComment) {
//                val action = OpenClaimFragmentDirections
//                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(claimComment)
//                findNavController().navigate(action)
                val bundle = Bundle().apply {
                    putParcelable("comment", claimComment)
                }
                findNavController().navigate(
                    R.id.action_openClaimFragment_to_createEditClaimCommentFragment,
                    bundle
                )
            }
        })

        binding.apply {
            titleTextView.text = claim.claim.title
            executorNameTextView.text = if (claim.executor != null) {
                Utils.fullUserNameGenerator(
                    claim.executor.lastName.toString(),
                    claim.executor.firstName.toString(),
                    claim.executor.middleName.toString()
                )
            } else {
                getText(R.string.not_assigned)
            }
            planeDateTextView.text = claim.claim.planExecuteDate.toString()
            statusLabelTextView.text = when (claim.claim.status) {
                Claim.Status.CANCELLED -> getString(R.string.cancel)
                Claim.Status.EXECUTED -> getString(R.string.executed)
                Claim.Status.IN_PROGRESS -> getString(R.string.in_progress)
                Claim.Status.OPEN -> getString(R.string.status_open)
                null -> "?"
            }
            descriptionTextView.text = claim.claim.description
            authorNameTextView.text = Utils.fullUserNameGenerator(
                claim.creator?.lastName.toString(),
                claim.creator?.firstName.toString(),
                claim.creator?.middleName.toString()
            )
            createDataTextView.text = claim.claim.createDate.toString()

            addImageButton.setOnClickListener {
                findNavController()
                    .navigate(R.id.action_openClaimFragment_to_createEditClaimCommentFragment)
            }

            closeImageButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        binding.claimCommentsListRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.commentsData.collect {
                adapter.submitList(it)
            }
        }
    }
}
