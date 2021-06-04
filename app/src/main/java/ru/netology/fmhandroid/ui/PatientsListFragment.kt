package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.OnInterractionListener
import ru.netology.fmhandroid.adapter.PatientListAdapter
import ru.netology.fmhandroid.databinding.FragmentPatientsListBinding
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum
import ru.netology.fmhandroid.viewmodel.PatientViewModel

class PatientsListFragment : Fragment() {
    private val viewModel: PatientViewModel by viewModels(
            ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentPatientsListBinding.inflate(inflater, container, false)

        val adapter = PatientListAdapter(object : OnInterractionListener {
            override fun onOpenCard(patient: Patient) {
                TODO("Not yet implemented")
            }
        })

        binding.recyclerPatientsList.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner,
                { state ->
                    binding.progress.isVisible = state.loading
//                    binding.errorLoadingGroup.isVisible = state.errorLoading
                    binding.errorSavingGroup.isVisible = state.errorSaving
                })

        binding.retryLoadingButton.setOnClickListener {
            viewModel.loadPatientsList()
        }

        viewModel.data.observe(viewLifecycleOwner
        ) { state ->
            adapter.submitList(
                state.dto.map {
                    it as Patient
                })
            binding.emptyText.isVisible = state.empty
        }

        binding.addPatient.setOnClickListener {
            AddPatientFragment().show(parentFragmentManager, "AddPatientFragment")
        }

        binding.topAppBar.setNavigationOnClickListener { view ->
            PopupMenu(view.context, view).apply {
                inflate(R.menu.top_app_bar)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.active -> {
                            viewModel.getAllPatientsWithAdmissionStatus(
                                PatientStatusEnum.ACTIVE
                            )
                            true
                        }
                        R.id.expected -> {
                            viewModel.getAllPatientsWithAdmissionStatus(
                                PatientStatusEnum.EXPECTED
                            )
                            true
                        }
                        R.id.discharged -> {
                            viewModel.getAllPatientsWithAdmissionStatus(
                                PatientStatusEnum.DISCHARGED
                            )
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }

        return binding.root
    }
}