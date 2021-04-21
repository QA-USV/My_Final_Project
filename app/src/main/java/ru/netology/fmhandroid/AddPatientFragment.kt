package ru.netology.fmhandroid

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
import ru.netology.fmhandroid.databinding.AddPatientCardBinding
import ru.netology.fmhandroid.viewmodel.FmhViewModel
import ru.netology.fmhandroid.viewmodel.emptyPatient

class AddPatientFragment : DialogFragment() {
    private val viewModel: FmhViewModel by viewModels(
            ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val binding = AddPatientCardBinding.inflate(inflater, container, false)

        binding.saveButton.setOnClickListener {
            val patient = emptyPatient.copy(
                    lastName = binding.newPatientLastName.editText?.text.toString(),
                    firstName = binding.newPatientName.editText?.text.toString(),
                    middleName = binding.newPatientMiddleName.editText?.text.toString(),
                    birthDate = binding.newPatientBirthDate.editText?.text.toString())
            if (patient.lastName.isNotBlank() && patient.firstName.isNotBlank() && patient.middleName.isNotBlank()) {
                viewModel.changePatientData(
                        patient.lastName,
                        patient.firstName,
                        patient.middleName,
                        patient.birthDate)
                viewModel.save()
            } else {
                Toast.makeText(activity, R.string.toast_empty_field, Toast.LENGTH_LONG).show()
            }
            viewModel.patientCreated.observe(viewLifecycleOwner, {
                dismiss()
            })
        }

        binding.cancelButton.setOnClickListener {
            val dialog = activity?.let { activity ->
                AlertDialog.Builder(activity)
            }

            dialog
                    ?.setMessage(R.string.cancelation)
                    ?.setPositiveButton(R.string.add_patient_fragment_positive_button) { dialog, int ->
                        dismiss()
                    }
                    ?.setNegativeButton(R.string.add_patient_fragment_negative_button) { dialog, int ->
                        isCancelable
                    }
                    ?.create()
                    ?.show()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}


