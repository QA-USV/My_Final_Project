package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimCommentListAdapter
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.FullClaim
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimCardViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


//    TODO("В этом фрагменте после внедрения авторизации требуется изменить хардкод юзера на залогиненного пользователя!!!!")
@AndroidEntryPoint
class OpenClaimFragment : Fragment() {
    private lateinit var binding: FragmentOpenClaimBinding
    private val claimCardViewModel: ClaimCardViewModel by viewModels()

    val claimId: Int by lazy {
        val args by navArgs<OpenClaimFragmentArgs>()
        args.argClaim.claim.id!!
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        claimCardViewModel.init(claimId)

        lifecycleScope.launchWhenResumed {
            claimCardViewModel.openClaimCommentEvent.collect {
                val action = it.claimComment.claimId?.let { it1 ->
                    OpenClaimFragmentDirections
                        .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                            it,
                            it1
                        )
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenResumed {
            claimCardViewModel.claimStatusChangeExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }
    }

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

        binding.containerCustomAppBarIncludeOnFragmentOpenClaim.customAppBarTitleTextView.visibility =
            View.GONE
        binding.containerCustomAppBarIncludeOnFragmentOpenClaim.mainMenuImageButton.visibility =
            View.GONE
        binding.containerCustomAppBarIncludeOnFragmentOpenClaim.customAppBarSubTitleTextView
            .setText(R.string.claim)

        val adapter = ClaimCommentListAdapter(claimCardViewModel)

        binding.claimCommentsListRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            claimCardViewModel.dataFullClaim.collect { fullClaim ->
                renderingContentOfClaim(fullClaim, fullClaim.executor)
                adapter.submitList(fullClaim.comments)
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

    private fun displayingStatusOfClaim(claimStatus: Claim.Status) =
        when (claimStatus) {
            Claim.Status.CANCELLED -> getString(R.string.status_claim_canceled)
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
                statusProcessingMenu.menu.setGroupVisible(
                    R.id.in_progress_menu_group,
                    false
                )
            }
            Claim.Status.IN_PROGRESS -> {
                statusProcessingMenu.menu.setGroupVisible(R.id.open_menu_group, false)
                statusProcessingMenu.menu.setGroupVisible(
                    R.id.in_progress_menu_group,
                    true
                )
            }
            else -> {
                statusProcessingMenu.menu.clear()
                binding.statusProcessingImageButton.apply {
                    setImageResource(R.drawable.ic_status_processing_non_clickable)
                }
            }
        }
    }

    private fun renderingContentOfClaim(
        fullClaim: FullClaim,
        executor: User?
    ) {
        val statusProcessingMenu = PopupMenu(context, binding.statusProcessingImageButton)
        statusProcessingMenu.inflate(R.menu.menu_claim_status_processing)
        binding.titleTextView.text = fullClaim.claim.title
        binding.planeDateTextView.text =
            fullClaim.claim.planExecuteDate?.let { Utils.formatDate(it) }
        binding.planTimeTextView.text =
            fullClaim.claim.planExecuteDate?.let { Utils.formatTime(it) }
        binding.descriptionTextView.text = fullClaim.claim.description
        binding.authorNameTextView.text = Utils.fullUserNameGenerator(
            fullClaim.creator.lastName.toString(),
            fullClaim.creator.firstName.toString(),
            fullClaim.creator.middleName.toString()
        )
        binding.createDataTextView.text =
            fullClaim.claim.createDate?.let { Utils.formatDate(it) }
        binding.createTimeTextView.text =
            fullClaim.claim.createDate?.let { Utils.formatTime(it) }
        binding.statusLabelTextView.text =
            fullClaim.claim.status?.let { displayingStatusOfClaim(it) }

        statusMenuVisibility(
            fullClaim.claim.status!!,
            statusProcessingMenu
        )

        binding.executorNameTextView.text =
            if (fullClaim.executor != null) {
                Utils.fullUserNameGenerator(
                    executor?.lastName.toString(),
                    executor?.firstName.toString(),
                    executor?.middleName.toString()
                )
            } else {
                getString(R.string.not_assigned)
            }

        binding.editProcessingImageButton.apply {
            if (fullClaim.claim.status == Claim.Status.OPEN) {
                this.setImageResource(R.drawable.ic_pen)
                this.isClickable = true
                this.setOnClickListener {
                    val action = OpenClaimFragmentDirections
                        .actionOpenClaimFragmentToCreateEditClaimFragment(fullClaim)
                    findNavController().navigate(action)
                }
            } else {
                this.setImageResource(R.drawable.ic_pen_light)
                this.isClickable = false
                this.setOnClickListener {
                    showErrorToast(R.string.inability_to_edit_claim)
                }
            }
        }

        when (fullClaim.claim.status) {
            Claim.Status.OPEN -> {
                // Заменить на залогиненного юзера и добавить в условие админа !!
                statusProcessingMenu.menu.findItem(R.id.cancel_list_item).isEnabled =
                    user.id == fullClaim.claim.creatorId
            }
            Claim.Status.IN_PROGRESS -> {
                if (user.id != fullClaim.claim.executorId) {
                    binding.statusProcessingImageButton.setImageResource(R.drawable.ic_status_processing_non_clickable)
                    statusProcessingMenu.menu.clear()
                }
            }
            Claim.Status.CANCELLED -> {
            }
            else -> returnTransition
        }

        binding.closeImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.statusProcessingImageButton.setOnClickListener {
            statusProcessingMenu.show()
        }

        binding.addCommentImageButton.setOnClickListener {
            val action = OpenClaimFragmentDirections
                .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                    argComment = null,
                    argClaimId = fullClaim.claim.id!!
                )
            findNavController().navigate(action)
        }

        statusProcessingMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.in_progress_list_item -> {

                    // Изменить claimExecutor на залогиненного пользователя !!!
                    claimCardViewModel.changeClaimStatus(
                        claimId = fullClaim.claim.id!!,
                        newClaimStatus = Claim.Status.IN_PROGRESS,
                        executorId = user.id,
                        claimComment = Utils.EmptyComment.emptyClaimComment
                    )
                    true
                }

                R.id.cancel_list_item -> {
                    claimCardViewModel.changeClaimStatus(
                        fullClaim.claim.id!!,
                        Claim.Status.CANCELLED,
                        executorId = null,
                        claimComment = Utils.EmptyComment.emptyClaimComment
                    )
                    true
                }

                R.id.throw_off_list_item -> {
                    val view = requireActivity().layoutInflater.inflate(
                        R.layout.fragment_dialog_comment_create,
                        null
                    )

                    val dialog = AlertDialog.Builder(requireContext())
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, null)
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()

                    dialog.setOnShowListener {
                        val buttonOk: Button =
                            (dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                        val buttonCancel: Button =
                            (dialog).getButton(AlertDialog.BUTTON_NEGATIVE)
                        buttonOk.setOnClickListener {
                            val editText = view.findViewById<EditText>(R.id.editText)
                            val text = editText.text
                            if (text.isBlank()) {
                                showErrorToast(R.string.toast_empty_field)
                            } else {
                                claimCardViewModel.changeClaimStatus(
                                    fullClaim.claim.id!!,
                                    Claim.Status.OPEN,
                                    executorId = null,
                                    claimComment = ClaimComment(
                                        claimId = fullClaim.claim.id,
                                        description = text.toString(),
                                        creatorId = user.id,
                                        createDate = LocalDateTime.now()
                                            .toEpochSecond(
                                                ZoneId.of("Europe/Moscow").rules.getOffset(
                                                    Instant.now()
                                                )
                                            )
                                    )
                                )
                                dialog.dismiss()
                            }
                        }
                        buttonCancel.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                    dialog.show()
                    true
                }

                R.id.executes_list_item -> {
                    val view = requireActivity().layoutInflater.inflate(
                        R.layout.fragment_dialog_comment_create,
                        null
                    )

                    val dialog = AlertDialog.Builder(requireContext())
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, null)
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()

                    dialog.setOnShowListener {
                        val buttonOk: Button =
                            (dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                        val buttonCancel: Button =
                            (dialog).getButton(AlertDialog.BUTTON_NEGATIVE)
                        buttonOk.setOnClickListener {
                            val editText = view.findViewById<EditText>(R.id.editText)
                            val text = editText.text
                            if (text.isBlank()) {
                                showErrorToast(R.string.toast_empty_field)
                            } else {
                                claimCardViewModel.changeClaimStatus(
                                    fullClaim.claim.id!!,
                                    Claim.Status.EXECUTED,
                                    executorId = fullClaim.executor?.id,
                                    claimComment = ClaimComment(
                                        claimId = fullClaim.claim.id,
                                        description = text.toString(),
                                        creatorId = user.id,
                                        createDate = LocalDateTime.now().toEpochSecond(
                                            ZoneId.of("Europe/Moscow").rules.getOffset(
                                                Instant.now()
                                            )
                                        )
                                    )
                                )
                                dialog.dismiss()
                            }
                        }
                        buttonCancel.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                    dialog.show()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
