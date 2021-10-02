package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentCreateEditCommentBinding
import ru.netology.fmhandroid.dto.WishComment
import ru.netology.fmhandroid.dto.WishCommentWithCreator
import ru.netology.fmhandroid.viewmodel.WishViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class CreateEditWishCommentFragment : Fragment() {
    private val viewModel: WishViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val args: CreateEditWishCommentFragmentArgs by navArgs()
        val comment: WishCommentWithCreator? = args.argComment
        val claimId: Int = args.argWishId

        val binding = FragmentCreateEditCommentBinding.inflate(
            inflater,
            container,
            false
        )

        if (comment != null) {
            binding.commentTextInputLayout.editText?.setText(comment.wishComment.description)

            binding.saveButton.setOnClickListener {
                val newCommentDescription = binding.commentTextInputLayout.editText?.text.toString()
                if (newCommentDescription.isNotBlank()) {
                    viewModel.updateWishComment(
                        comment.wishComment.copy(
                            description = binding.commentTextInputLayout.editText?.text.toString()
                        )
                    )
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.toast_empty_field,
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

            }
        } else {
            binding.saveButton.setOnClickListener {
                // TODO("Доработать когда будет реализована авторизация
                //  Вставлять корректный creatorId вместо хардкода")

                val newCommentDescription = binding.commentTextInputLayout.editText?.text.toString()

                if (newCommentDescription.isNotBlank()) {
                    viewModel.createClaimComment(
                        WishComment(
                            wishId = claimId,
                            description = newCommentDescription,
                            creatorId = 1,
                            createDate = LocalDateTime.now().toEpochSecond(
                                ZoneId.of("Europe/Moscow").rules.getOffset(
                                    Instant.now()))
                        )
                    )
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.toast_empty_field,
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}