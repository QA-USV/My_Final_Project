package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.OnInteractionListener
import ru.netology.fmhandroid.adapter.PatientListAdapter
import ru.netology.fmhandroid.databinding.FragmentPatientsListBinding
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.Patient.Status
import ru.netology.fmhandroid.viewmodel.PatientViewModel

@AndroidEntryPoint
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

        val adapter = PatientListAdapter(object : OnInteractionListener {
            override fun onOpenCard(patient: Patient) {
                TODO("Not yet implemented")
            }
        })

        lifecycleScope.launchWhenCreated {
            viewModel.data
                .collectLatest { state ->
                    adapter.submitList(state)
                    binding.emptyText.isVisible = state.isEmpty()
                }
        }

        binding.recyclerPatientsList.adapter = adapter

        binding.addPatient.setOnClickListener {
            AddPatientFragment().show(parentFragmentManager, "AddPatientFragment")
        }

        viewModel.loadPatientExceptionEvent.observe(viewLifecycleOwner, {
            val activity = activity ?: return@observe
            val dialog = activity.let { activity ->
                AlertDialog.Builder(activity)
            }
            dialog.setMessage(R.string.error_loading)
                .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        })

        binding.topAppBar.setNavigationOnClickListener { view ->
            PopupMenu(view.context, view).apply {
                inflate(R.menu.top_app_bar)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.active -> {
                            viewModel.getAllPatientsWithAdmissionStatus(
                                Status.ACTIVE
                            )
                            true
                        }
                        R.id.expected -> {
                            viewModel.getAllPatientsWithAdmissionStatus(
                                Status.EXPECTED
                            )
                            true
                        }
                        R.id.discharged -> {
                            viewModel.getAllPatientsWithAdmissionStatus(
                                Status.DISCHARGED
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