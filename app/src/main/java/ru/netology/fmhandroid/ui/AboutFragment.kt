package ru.netology.fmhandroid.ui

import android.app.job.JobInfo
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import ru.netology.fmhandroid.BuildConfig
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentAboutBinding

class AboutFragment: Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAboutBinding.bind(view)
        binding.aboutVersionValueTextView.text = BuildConfig.VERSION_NAME
        binding.aboutPrivacyPolicyTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}