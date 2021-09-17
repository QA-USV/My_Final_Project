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
import ru.netology.fmhandroid.dto.ClaimCommentWithCreator
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

class CreateEditCommentFragment : Fragment() {
    private val viewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: CreateEditCommentFragmentArgs by navArgs()
        val comment: ClaimCommentWithCreator? = args.argComment

        val binding = FragmentCreateEditCommentBinding.inflate(
            inflater,
            container,
            false
        )

        if (comment != null) {
            binding.commentTextInputLayout.editText?.setText(comment.claimComment.description)

            binding.saveButton.setOnClickListener {
                val newCommentDescription = binding.commentTextInputLayout.editText?.text.toString()
                if (newCommentDescription.isNotBlank()) {
                    viewModel.updateClaimComment(
                        comment.claimComment.copy(
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
                }

            }
        } else {
            binding.saveButton.setOnClickListener {
                TODO("Доработать когда будет реализована авторизация")
            }
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}