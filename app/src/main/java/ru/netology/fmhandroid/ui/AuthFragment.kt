package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentAuthBinding
import ru.netology.fmhandroid.viewmodel.AuthViewModel

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth){
    private lateinit var binding: FragmentAuthBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.loginEvent.collectLatest {
                findNavController().navigate(R.id.action_authFragment_to_mainFragment)
            }
        }
        lifecycleScope.launch {
            viewModel.loginExceptionEvent.collectLatest {
                Toast.makeText(
                    requireContext(),
                    R.string.wrong_login_or_password,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAuthBinding.bind(view)

        binding.enterButton.setOnClickListener {
            if (binding.loginTextInputLayout.editText?.text.isNullOrBlank() || binding.passwordTextInputLayout.editText?.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    R.string.empty_login_or_password,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.login(
                    binding.loginTextInputLayout.editText?.text.toString().trim(),
                    binding.passwordTextInputLayout.editText?.text.toString().trim()
                )
            }
        }
    }
}