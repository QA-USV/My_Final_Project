package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.fmhandroid.databinding.FragmentCreateEditCommentBinding
import ru.netology.fmhandroid.viewmodel.ClaimViewModel

class CreateEditCommentFragment: Fragment() {
    private val viewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: CreateEditCommentFragmentArgs by navArgs()
        val comment = args.argComment

        val binding = FragmentCreateEditCommentBinding.inflate(inflater, container, false)

        binding.commentTextInputLayout.editText?.setText(comment.description)
        binding.saveButton.setOnClickListener {
            viewModel
        }
        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}