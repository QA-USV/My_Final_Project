package ru.netology.fmhandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.databinding.FragmentPatientBinding

@AndroidEntryPoint
class PatientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentPatientBinding.inflate(
            inflater,
            container,
            false
        )



        return binding.root
    }
}