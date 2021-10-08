package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimCommentListAdapter
import ru.netology.fmhandroid.adapter.OnClaimCommentItemClickListener
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.utils.Events
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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

    //    TODO("В этом фрагменте после внедрения авторизации требуется изменить хардкод юзера на залогиненного пользователя!!!!")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOpenClaimBinding.bind(view)

        val args: OpenClaimFragmentArgs by navArgs()
        val claim = args.argClaim

        // Временная переменная. После авторизации заменить на залогиненного юзера
        val user = User(
            id = 1,
            login = "User-1",
            password = "abcd",
            firstName = "Дмитрий",
            lastName = "Винокуров",
            middleName = "Владимирович",
            phoneNumber = "+79109008765",
            email = "Vinokurov@mail.ru",
            deleted = false
        )

        val adapter = ClaimCommentListAdapter(object : OnClaimCommentItemClickListener {
            override fun onCard(claimComment: ClaimCommentWithCreator) {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                        claimComment,
                        claim.claim.id!!
                    )
                findNavController().navigate(action)
            }
        })

        val statusProcessingMenu = PopupMenu(context, binding.statusProcessingImageButton)
        statusProcessingMenu.inflate(R.menu.menu_claim_status_processing)

        statusMenuVisibility(claim.claim.status!!, statusProcessingMenu)
        statusProcessingMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.take_to_work_list_item -> {
                    // Изменить claimExecutor на залогиненного пользователя !!!
                    viewModel.changeClaimStatus(
                        claimId = claim.claim.id!!,
                        newClaimStatus = Claim.Status.IN_PROGRESS,
                        executorId = user.id,
                        claimComment = Utils.EmptyComment.emptyClaimComment
                    )

                    lifecycleScope.launchWhenStarted {
                        Events.events.collect { event ->
                            when (event) {
                                viewModel.claimStatusChangeExceptionEvent -> {
                                    showErrorToast()
                                    return@collect
                                }
                            }

                            viewModel.dataClaim.collect {
                                binding.statusLabelTextView.text =
                                    displayingStatusOfClaim(it.claim.status!!)
                                statusMenuVisibility(
                                    it.claim.status!!,
                                    statusProcessingMenu
                                )
                                binding.executorNameTextView.text = if (it.executor != null) {
                                    Utils.fullUserNameGenerator(
                                        it.executor.lastName.toString(),
                                        it.executor.firstName.toString(),
                                        it.executor.middleName.toString()
                                    )
                                } else {
                                    getString(R.string.not_assigned)
                                }
                            }
                        }
                    }
                    true
                }

                R.id.cancel_list_item -> {
                    lifecycleScope.launchWhenStarted {
                        // Изменить в условии на Id залогиненного юзера!!!
                        if (claim.claim.creatorId != user.id) {
                            showWarningSnackBar(R.string.no_change_status_rights_author)
                            return@launchWhenStarted
                        }

                        viewModel.changeClaimStatus(
                            claim.claim.id!!,
                            Claim.Status.CANCELLED,
                            executorId = null,
                            claimComment = Utils.EmptyComment.emptyClaimComment
                        )
                        Events.events.collect { event ->
                            when (event) {
                                viewModel.claimStatusChangeExceptionEvent -> {
                                    showErrorToast()
                                    return@collect
                                }
                            }

                            viewModel.dataClaim.collect {
                                binding.statusLabelTextView.text =
                                    displayingStatusOfClaim(it.claim.status!!)
                                statusMenuVisibility(
                                    it.claim.status!!,
                                    statusProcessingMenu
                                )
                                binding.executorNameTextView.text = if (it.executor != null) {
                                    Utils.fullUserNameGenerator(
                                        it.executor.lastName.toString(),
                                        it.executor.firstName.toString(),
                                        it.executor.middleName.toString()
                                    )
                                } else {
                                    getString(R.string.not_assigned)
                                }
                            }
                        }
                    }
                    true
                }

                R.id.throw_off_list_item -> {
                    var newClaimComment = Utils.EmptyComment.emptyClaimComment

                    val dialog = CreateCommentDialogFragment.newInstance(
                        text = "",
                        hint = "Description",
                        isMultiline = true
                    )
                    dialog.onOk = {
                        val text = dialog.editText.text
                        if (text.isNotBlank()) {
                            newClaimComment =
                                ClaimComment(
                                    claimId = claim.claim.id,
                                    description = text.toString(),
                                    creatorId = user.id,
                                    createDate = LocalDateTime.now().toEpochSecond(
                                        ZoneId.of("Europe/Moscow").rules.getOffset(
                                            Instant.now()
                                        )
                                    )
                                )

                        } else {
                            Toast.makeText(
                                requireContext(),
                                R.string.toast_empty_field,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    dialog.show(this.childFragmentManager, "CreateCommentDialog")

                    lifecycleScope.launchWhenStarted {

                        if (user.id != claim.claim.executorId) {
                            showWarningSnackBar(R.string.no_change_status_rights_executor)
                            return@launchWhenStarted
                        }

                        viewModel.changeClaimStatus(
                            claim.claim.id!!,
                            Claim.Status.OPEN,
                            executorId = null,
                            claimComment = newClaimComment
                        )

                        Events.events.collect { event ->
                            when (event) {
                                viewModel.claimStatusChangeExceptionEvent -> {
                                    showErrorToast()
                                    return@collect
                                }
                            }

                            viewModel.dataClaim.collect {
                                binding.statusLabelTextView.text =
                                    displayingStatusOfClaim(it.claim.status!!)
                                statusMenuVisibility(
                                    it.claim.status!!,
                                    statusProcessingMenu
                                )
                                binding.executorNameTextView.text = if (it.executor != null) {
                                    Utils.fullUserNameGenerator(
                                        it.executor.lastName.toString(),
                                        it.executor.firstName.toString(),
                                        it.executor.middleName.toString()
                                    )
                                } else {
                                    getString(R.string.not_assigned)
                                }
                            }
                        }
                    }
                    true
                }

                R.id.executes_list_item -> {
                    var newClaimComment = Utils.EmptyComment.emptyClaimComment

                    val dialog = CreateCommentDialogFragment.newInstance(
                        text = "",
                        hint = "Description",
                        isMultiline = true
                    )
                    dialog.onOk = {
                        val text = dialog.editText.text
                        if (text.isNotBlank()) {
                            newClaimComment =
                                ClaimComment(
                                    claimId = claim.claim.id,
                                    description = text.toString(),
                                    creatorId = user.id,
                                    createDate = LocalDateTime.now().toEpochSecond(
                                        ZoneId.of("Europe/Moscow").rules.getOffset(
                                            Instant.now()
                                        )
                                    )
                                )

                        } else {
                            Toast.makeText(
                                requireContext(),
                                R.string.toast_empty_field,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    dialog.show(this.childFragmentManager, "CreateCommentDialog")

                    lifecycleScope.launchWhenStarted {
                        if (user.id != claim.claim.executorId) {
                            showWarningSnackBar(R.string.no_change_status_rights_executor)
                            return@launchWhenStarted
                        }

                        viewModel.changeClaimStatus(
                            claim.claim.id!!,
                            Claim.Status.EXECUTED,
                            executorId = claim.executor?.id,
                            claimComment = newClaimComment
                        )

                        Events.events.collect { event ->
                            when (event) {
                                viewModel.claimStatusChangeExceptionEvent -> {
                                    showErrorToast()
                                    return@collect
                                }
                            }

                            viewModel.dataClaim.collect {
                                binding.statusLabelTextView.text =
                                    displayingStatusOfClaim(it.claim.status!!)
                                statusMenuVisibility(
                                    it.claim.status!!,
                                    statusProcessingMenu
                                )
                                binding.executorNameTextView.text = if (it.executor != null) {
                                    Utils.fullUserNameGenerator(
                                        it.executor.lastName.toString(),
                                        it.executor.firstName.toString(),
                                        it.executor.middleName.toString()
                                    )
                                } else {
                                    getString(R.string.not_assigned)
                                }
                            }
                        }
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

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

            planeDateTextView.text =
                claim.claim.planExecuteDate?.let { Utils.showDateTimeInOne(it) }

            statusLabelTextView.text = displayingStatusOfClaim(claim.claim.status!!)

            descriptionTextView.text = claim.claim.description
            authorNameTextView.text = Utils.fullUserNameGenerator(
                claim.creator.lastName.toString(),
                claim.creator.firstName.toString(),
                claim.creator.middleName.toString()
            )
            createDataTextView.text =
                claim.claim.createDate?.let { Utils.showDateTimeInOne(it) }

            addCommentImageButton.setOnClickListener {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                        argComment = null,
                        argClaimId = claim.claim.id!!
                    )
                findNavController().navigate(action)
            }

            closeImageButton.setOnClickListener {
                findNavController().navigateUp()
            }

            statusProcessingImageButton.setOnClickListener {
                statusProcessingMenu.show()
            }

            editProcessingImageButton.setOnClickListener {
                if (claim.claim.status != Claim.Status.OPEN) {
                    showWarningSnackBar(R.string.inability_to_edit_claim)
                    return@setOnClickListener
                }
                // Изменить в условии на Id залогиненного юзера!!!
                if (claim.claim.creatorId != user.id) {
                    showWarningSnackBar(R.string.no_editing_rights)
                    return@setOnClickListener
                }
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimFragment(claim)
                findNavController().navigate(action)
            }
        }

        binding.claimCommentsListRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            Events.events.collect {
                viewModel.claimCommentUpdatedEvent
                viewModel.commentsData.collect {
                    adapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.commentsData.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenResumed {
            Events.events.collect {
                viewModel.claimUpdatedEvent
                viewModel.dataClaim.collect { updated ->
                    binding.apply {
                        titleTextView.text = updated.claim.title
                        executorNameTextView.text = if (updated.executor != null) {
                            Utils.fullUserNameGenerator(
                                updated.executor.lastName.toString(),
                                updated.executor.firstName.toString(),
                                updated.executor.middleName.toString()
                            )
                        } else {
                            getText(R.string.not_assigned)
                        }

                        planeDateTextView.text =
                            updated.claim.planExecuteDate?.let { Utils.showDateTimeInOne(it) }
                        statusLabelTextView.text = displayingStatusOfClaim(updated.claim.status!!)
                        descriptionTextView.text = updated.claim.description
                        authorNameTextView.text = Utils.fullUserNameGenerator(
                            updated.creator.lastName.toString(),
                            updated.creator.firstName.toString(),
                            updated.creator.middleName.toString()
                        )
                        createDataTextView.text =
                            updated.claim.createDate?.let { Utils.showDateTimeInOne(it) }
                    }
                }
            }
        }
    }

    private fun showWarningSnackBar(warningTextString: Int) {
        Snackbar.make(
            binding.root,
            warningTextString,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("Ok") { return@setAction }
            .setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.app_snack_bar_background
                )
            )
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            .show()
    }

    private fun showErrorToast() {
        Toast.makeText(
            requireContext(),
            R.string.error,
            Toast.LENGTH_LONG
        ).show()
    }

//    private fun createClaimCommentDialog(
//        claim: ClaimWithCreatorAndExecutor,
//        user: User
//    ) {
//        val dialog = CreateCommentDialogFragment.newInstance(
//            text = "",
//            hint = "Description",
//            isMultiline = true
//        )
//        dialog.onOk = {
//            val text = dialog.editText.text
//            if (text.isNotBlank()) {
//                viewModel.createClaimComment(
//                    ClaimComment(
//                        claimId = claim.claim.id,
//                        description = text.toString(),
//                        creatorId = user.id,
//                        createDate = LocalDateTime.now().toEpochSecond(
//                            ZoneId.of("Europe/Moscow").rules.getOffset(
//                                Instant.now()
//                            )
//                        )
//                    )
//                )
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    R.string.toast_empty_field,
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//        dialog.show(this.childFragmentManager, "CreateCommentDialog")
//    }

    private fun displayingStatusOfClaim(claimStatus: Claim.Status) =
        when (claimStatus) {
            Claim.Status.CANCELLED -> getString(R.string.cancel)
            Claim.Status.EXECUTED -> getString(R.string.executed)
            Claim.Status.IN_PROGRESS -> getString(R.string.in_progress)
            Claim.Status.OPEN -> getString(R.string.status_open)
        }

    private fun statusMenuVisibility(
        claimStatus: Claim.Status,
        statusProcessingMenu: PopupMenu
    ) {
        when (claimStatus) {
            Claim.Status.OPEN -> {
                statusProcessingMenu.menu.setGroupVisible(R.id.open_menu_group, true)
                statusProcessingMenu.menu.setGroupVisible(R.id.in_progress_menu_group, false)
            }
            Claim.Status.IN_PROGRESS -> {
                statusProcessingMenu.menu.setGroupVisible(R.id.open_menu_group, false)
                statusProcessingMenu.menu.setGroupVisible(R.id.in_progress_menu_group, true)
            }
            else -> {
                return
            }
        }
    }
}
