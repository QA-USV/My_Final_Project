package ru.netology.fmhandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentPatientListBinding
import ru.netology.fmhandroid.databinding.ItemPatientBinding
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.Patient.Status
import ru.netology.fmhandroid.utils.Utils.shortUserNameGenerator
import ru.netology.fmhandroid.utils.Utils.showDate

interface PatientsOnInteractionListener {
    fun onOpenCard(patient: Patient) {}
}

class PatientListAdapter(
    private val onInteractionListener: PatientsOnInteractionListener
) : ListAdapter<Patient, PatientListAdapter.PatientViewHolder>(PatientDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PatientViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class PatientViewHolder(
        private val binding: ItemPatientBinding,
        private val onInteractionListener: PatientsOnInteractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(patient: Patient) = with(binding) {
            patientNameTextView.text = itemView.resources.getString(
                R.string.full_name_format,
                patient.firstName?.first()?.plus("."),
                patient.middleName?.first()?.plus("."),
                patient.lastName
            )
            birthDateTextView.text = patient.birthDate?.let { showDate(it) }
        }
    }
}

class PatientDiffCallBack : DiffUtil.ItemCallback<Patient>() {
    override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
        return oldItem == newItem
    }
}