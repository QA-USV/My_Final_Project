package ru.netology.fmhandroid.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {
    private lateinit var vDatePicker : TextInputEditText
    private lateinit var vTimePicker : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAddNoteBinding.inflate(
            inflater,
            container,
            false
        )

        /* DropMenuPatient */
        val patientDropMenuItems = listOf("Иванов Иван Иванович", "Петров Пётр Петрович", "Сидоров Сидор Сидорович",)
        val patientArrayAdapter = ArrayAdapter(requireContext(), R.layout.menu_item, patientDropMenuItems)
        (binding.vPatientDropMenu.editText as? AutoCompleteTextView)?.setAdapter(patientArrayAdapter)

        /* DropMenuExecutor */
        val executorDropMenuItems = listOf("Иван Арнольдович Борменталь", "Грегори Хаус", "Персиваль Улисс Кокс", "Джон Дориан",)
        val executorArrayAdapter = ArrayAdapter(requireContext(), R.layout.menu_item, executorDropMenuItems)
        (binding.vExecutorDropMenu.editText as? AutoCompleteTextView)?.setAdapter(executorArrayAdapter)

        val myCalendar = Calendar.getInstance()
        vDatePicker = binding.vDateInPlanField
        vTimePicker = binding.vTimeInPlanField


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


        return binding.root
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