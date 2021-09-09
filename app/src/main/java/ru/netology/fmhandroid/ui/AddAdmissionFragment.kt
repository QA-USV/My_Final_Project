package ru.netology.fmhandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.databinding.FragmentCreateEditAdmissionBinding

@AndroidEntryPoint
class AddAdmissionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentCreateEditAdmissionBinding.inflate(
            inflater,
            container,
            false
        )



        return binding.root
    }
}