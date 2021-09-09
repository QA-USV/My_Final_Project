package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.Patient.Status

interface OnInteractionListener {
    fun onOpenCard(patient: Patient) {}
}

//class PatientListAdapter(
//    private val onInteractionListener: OnInteractionListener
//) : ListAdapter<Patient, PatientListAdapter.PatientViewHolder>(PatientDiffCallBack()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
//        val binding = PatientsListCardBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//
//        return PatientViewHolder(binding, onInteractionListener)
//    }
//
//    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
//        getItem(position)?.let {
//            holder.bind(it)
//        }
//    }
//
//    class PatientViewHolder(
//        private val binding: PatientsListCardBinding,
//        private val onInteractionListener: OnInteractionListener,
//    ) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(patient: Patient) = with(binding) {
//            nameTextView.text = itemView.resources.getString(
//                R.string.patient_full_name_format,
//                patient.lastName, patient.firstName, patient.middleName
//            )
//
//            val patientStatusResId = when (patient.admissionsStatus) {
//                Status.ACTIVE -> R.string.in_hospice
//                Status.EXPECTED -> R.string.expected
//                Status.DISCHARGED -> R.string.discharged
//                else -> null
//            }
//            statusTextView.text = patientStatusResId?.let { itemView.context.getString(it) }
//
//            nameTextView.setOnClickListener {
//                onInteractionListener.onOpenCard(patient)
//            }
//        }
//
//        private val Status.textResId
//            get() = when (this) {
//                Status.ACTIVE -> R.string.in_hospice
//                Status.EXPECTED -> R.string.expected
//                Status.DISCHARGED -> R.string.discharged
//            }
//    }
//}

class PatientDiffCallBack : DiffUtil.ItemCallback<Patient>() {
    override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem == newItem
    }
}