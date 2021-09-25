package ru.netology.fmhandroid.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentCreateEditClaimBinding
import ru.netology.fmhandroid.viewmodel.ClaimViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateEditClaimFragment : Fragment(R.layout.fragment_create_edit_claim) {
    private lateinit var vDatePicker : TextInputEditText
    private lateinit var vTimePicker : TextInputEditText
    private lateinit var binding: FragmentCreateEditClaimBinding

    private val viewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateEditClaimBinding.bind(view)

        /* DropMenuExecutor */
        val executorDropMenuItems = listOf("Иван Арнольдович Борменталь", "Грегори Хаус", "Персиваль Улисс Кокс", "Джон Дориан",)
        val executorArrayAdapter = ArrayAdapter(requireContext(), R.layout.menu_item, executorDropMenuItems)
        (binding.executorDropMenuTextInputLayout.editText as? AutoCompleteTextView)?.setAdapter(executorArrayAdapter)

        val myCalendar = Calendar.getInstance()
        vDatePicker = binding.dateInPlanTextInputEditText
        vTimePicker = binding.timeInPlanTextInputEditText

        /* DatePickerDialog */
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel(myCalendar)
        }

        vDatePicker.setOnClickListener {
            DatePickerDialog(this.requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        /* TimePickerDialog */
        val timePicker = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTimeLabel(myCalendar)
        }

        vTimePicker.setOnClickListener {
            TimePickerDialog(this.requireContext(), timePicker, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(
                Calendar.MINUTE), true).show()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleTextInputLayout.toString()
            val executor = binding.executorDropMenuTextInputLayout.toString()
            val planExecuteDate = binding.dateInPlanTextInputLayout.toString()
            val planExecuteTime = binding.timeInPlanTextInputLayout.toString()
            val description = binding.descriptionTextInputLayout.toString()
// добавить обработку чекбоксов

            // Проработать по цепочке редактирование заявки!!!
//            viewModel.updateClaim(
//                title,
//                executor,
//                planExecuteDate,
//                planExecuteTime,
//                description
//            )
            if (title.isNotBlank() &&
                executor.isNotBlank() &&
                planExecuteDate.isNotBlank() &&
                planExecuteTime.isNotBlank() &&
                description.isNotBlank()
            ) {
//                viewModel.save()
            } else {
                Toast.makeText(this.context, R.string.toast_empty_field, Toast.LENGTH_LONG).show()
            }
        }

        binding.cancelButton.setOnClickListener {

        }
    }

    private fun updateDateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        vDatePicker.setText(sdf.format(myCalendar.time))
    }

    private fun updateTimeLabel(myCalendar: Calendar) {
        val myFormat = "HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        vTimePicker.setText(sdf.format(myCalendar.time))
    }
}