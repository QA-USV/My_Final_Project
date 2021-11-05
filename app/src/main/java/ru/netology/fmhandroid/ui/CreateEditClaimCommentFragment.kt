package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentCreateEditCommentBinding
import ru.netology.fmhandroid.dto.ClaimComment
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.viewmodel.ClaimCardViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class CreateEditClaimCommentFragment : Fragment(R.layout.fragment_create_edit_comment) {
    private val claimCardViewModel: ClaimCardViewModel by viewModels()
    lateinit var binding: FragmentCreateEditCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            claimCardViewModel.claimCommentCreateExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }
        lifecycleScope.launch {
            claimCardViewModel.updateClaimCommentExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }
        lifecycleScope.launch {
            claimCardViewModel.claimCommentCreatedEvent.collect {
                findNavController().navigateUp()
            }
        }
        lifecycleScope.launch {
            claimCardViewModel.claimCommentUpdatedEvent.collect {
                findNavController().navigateUp()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCreateEditCommentBinding.bind(view)

        val args: CreateEditClaimCommentFragmentArgs by navArgs()
        val comment: ClaimCommentWithCreator? = args.argComment
        val claimId: Int = args.argClaimId

        with(binding) {
            containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.mainMenuImageButton.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.authorizationImageButton.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.ourMissionImageButton.visibility =
                View.GONE
        }

        if (comment != null) {
            binding.containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.customAppBarTitleTextView.apply {
                setText(R.string.editing)
                textSize = 18F
            }
            binding.containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.customAppBarSubTitleTextView
                .setText(R.string.genitive_comment)

            binding.commentTextInputLayout.editText?.setText(comment.claimComment.description)

            binding.saveButton.setOnClickListener {
                val newCommentDescription = binding.commentTextInputLayout.editText?.text.toString()
                if (newCommentDescription.isNotBlank()) {
                    claimCardViewModel.updateClaimComment(
                        comment.claimComment.copy(
                            description = binding.commentTextInputLayout.editText?.text.toString()
                        )
                    )
                } else {
                    showErrorToast(R.string.toast_empty_field)
                    return@setOnClickListener
                }
            }
        } else {
            binding.containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.customAppBarTitleTextView.apply {
                setText(R.string.creating)
                textSize = 18F
            }
            binding.containerCustomAppBarIncludeOnFragmentCreateEditClaimComment.customAppBarSubTitleTextView
                .setText(R.string.genitive_comment)

            binding.saveButton.setOnClickListener {
                // TODO("Доработать когда будет реализована авторизация
                //  Вставлять корректный creatorId вместо хардкода")

                val newCommentDescription = binding.commentTextInputLayout.editText?.text.toString()

                if (newCommentDescription.isNotBlank()) {
                    claimCardViewModel.createClaimComment(
                        ClaimComment(
                            claimId = claimId,
                            description = newCommentDescription,
                            creatorId = 1,
                            createDate = LocalDateTime.now().toEpochSecond(
                                ZoneId.of("Europe/Moscow").rules.getOffset(
                                    Instant.now()
                                )
                            )
                        )
                    )
                } else {
                    showErrorToast(R.string.toast_empty_field)
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
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
