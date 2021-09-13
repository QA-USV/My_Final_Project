package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.Claim

@AndroidEntryPoint
class OpenClaimFragment: Fragment() {
    private lateinit var binding: FragmentOpenClaimBinding

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

        binding.apply {
            titleTextView.text = claim.claim.title
            executorNameTextView.text =
                getString(R.string.full_name_format, claim.executor.firstName, claim.executor.middleName, claim.executor.lastName)
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
    }
}