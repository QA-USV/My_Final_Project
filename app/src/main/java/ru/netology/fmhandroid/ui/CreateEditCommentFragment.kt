package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.netology.fmhandroid.databinding.FragmentCreateEditCommentBinding

class CreateEditCommentFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: CreateEditCommentFragmentArgs by navArgs()
        val comment = args.argComment

        val binding = FragmentCreateEditCommentBinding.inflate(inflater, container, false)

        binding.commentTextInputLayout.editText?.setText(comment.description)

        return binding.root
    }
}