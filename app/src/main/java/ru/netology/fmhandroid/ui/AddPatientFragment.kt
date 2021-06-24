package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.AddPatientCardBinding
import ru.netology.fmhandroid.viewmodel.PatientViewModel

class AddPatientFragment : DialogFragment() {
    private val viewModel: PatientViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = AddPatientCardBinding.inflate(inflater, container, false)

        binding.saveButton.setOnClickListener {
            val lastName = binding.setLastName.text.toString()
            val firstName = binding.setName.text.toString()
            val middleName = binding.setMiddleName.text.toString()
            val birthDate = binding.setBirthDate.text.toString()

            viewModel.changePatientData(
                lastName,
                firstName,
                middleName,
                birthDate
            )
            if (lastName.isNotBlank() &&
                firstName.isNotBlank() &&
                middleName.isNotBlank()
            ) {
                viewModel.save()
            } else {
                Toast.makeText(activity, R.string.toast_empty_field, Toast.LENGTH_LONG).show()
            }
            patientCreatedObserver()
        }

        binding.cancelButton.setOnClickListener {
            val activity = activity ?: return@setOnClickListener
            val dialog = activity.let { activity ->
                AlertDialog.Builder(activity)
            }

            dialog.setMessage(R.string.cancellation)
                .setPositiveButton(R.string.fragment_positive_button) { dialog, int ->
                    dismiss()
                }
                .setNegativeButton(R.string.fragment_negative_button) { dialog, int ->
                    isCancelable
                }
                .create()
                .show()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun patientCreatedObserver() {
        try {
            viewModel.patientCreatedEvent.observe(viewLifecycleOwner, {
                dismiss()
            })
        } catch (e: Exception) {
            e.printStackTrace()
            viewModel.savePatientExceptionEvent.observe(viewLifecycleOwner, {
                val activity = activity ?: return@observe
                val dialog = activity.let { activity ->
                    AlertDialog.Builder(activity)
                }
                dialog.setMessage(R.string.error_saving)
                    .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            })
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}


