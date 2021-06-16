package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.PatientsListCardBinding
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum

interface OnInterractionListener {
    fun onOpenCard(patient: Patient) {}
}

class PatientListAdapter(
    private val onInterractionListener: OnInterractionListener,
) : ListAdapter<Patient, PatientListAdapter.PatientViewHolder>(PatientDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = PatientsListCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PatientViewHolder(binding, onInterractionListener)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class PatientViewHolder(
        private val binding: PatientsListCardBinding,
        private val onInterractionListener: OnInterractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(patient: Patient) {
            binding.apply {
                (patient.lastName + " " + patient.firstName + " " + patient.middleName).also {
                    patientName.text = it
                }
                patientLocation.setText(
                    if (patient.status != null) {
                        patient.status.textResId
                    } else {
                        R.string.patient_status_is_not_determined
                    }
                )
                patientName.setOnClickListener {
                    onInterractionListener.onOpenCard(patient)
                }
            }
        }

        private val PatientStatusEnum.textResId
            get() = when (this) {
                PatientStatusEnum.ACTIVE -> R.string.patient_status_active
                PatientStatusEnum.EXPECTED -> R.string.patient_status_expected
                PatientStatusEnum.DISCHARGED -> R.string.patient_status_discharged
            }
    }

    class PatientDiffCallBack : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }
    }
}