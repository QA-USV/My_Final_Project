package ru.netology.fmhandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.netology.fmhandroid.databinding.FragmentAddAdmissionCardBinding

class AddAdmissionCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentAddAdmissionCardBinding.inflate(
            inflater,
            container,
            false
        )



        return binding.root
    }
}