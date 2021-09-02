package ru.netology.fmhandroid.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentAddNewsItemBinding
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNewsItemFragment : Fragment(R.layout.fragment_add_news_item) {
    private lateinit var vDatePicker: TextInputEditText
    private lateinit var vTimePicker: TextInputEditText
    private lateinit var binding: FragmentAddNewsItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewsItemBinding.bind(view)

        val newsCategoryItems = listOf(
            "Объявление",
            "День рождения",
            "Зарплата",
            "Профсоюз",
            "Праздник",
            "Массаж",
            "Благодарность",
            "Нужна помощь"
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, newsCategoryItems)
        (binding.newsItemCategoryTextInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (binding.newsItemCategoryTextInputLayout.editText as? AutoCompleteTextView)?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                val title = binding.newsItemTitleTextInputEditText
                newsCategoryItems.forEach { category ->
                    if (title.text.isNullOrBlank() || title.text.toString() == category) {
                        title.setText(selectedItem)
                    }
                }
            }

        val calendar = Calendar.getInstance()
        vDatePicker = binding.newsItemDateTextInputEditText

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel(calendar)
        }

        vDatePicker.setOnClickListener {
            DatePickerDialog(
                this.requireContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(
                    Calendar.MONTH
                ), calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        vTimePicker = binding.newsItemTimeTextInputEditText

        val timePicker = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            updateTimeLabel(calendar)
        }

        vTimePicker.setOnClickListener {
            TimePickerDialog(
                this.requireContext(), timePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(
                    Calendar.MINUTE
                ), true
            ).show()
        }
    }

    private fun updateDateLabel(calendar: Calendar) {
        val format = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        vDatePicker.setText(simpleDateFormat.format(calendar.time))
    }

    private fun updateTimeLabel(calendar: Calendar) {
        val format = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        vTimePicker.setText(simpleDateFormat.format(calendar.time))
    }
}