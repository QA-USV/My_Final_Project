package ru.netology.fmhandroid.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentCreateEditClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.utils.Utils.fullUserNameGenerator
import ru.netology.fmhandroid.utils.Utils.saveDateTime
import ru.netology.fmhandroid.viewmodel.ClaimViewModel
import ru.netology.fmhandroid.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateEditClaimFragment : Fragment(R.layout.fragment_create_edit_claim) {
    private lateinit var vDatePicker: TextInputEditText
    private lateinit var vTimePicker: TextInputEditText
    private lateinit var binding: FragmentCreateEditClaimBinding
    private var executorId: Int? = null

    //временно, пока нет авторизации
    private val creatorId = 1

    private val viewModelClaim: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val viewModelUser: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateEditClaimBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            viewModelUser.dataUser.collectLatest { users ->
                /* DropMenuExecutor */
                val executorDropMenuItems = users.map {
                    fullUserNameGenerator(
                        it.lastName!!,
                        it.firstName!!,
                        it.middleName!!
                    )
                }
                val executorArrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.menu_item, executorDropMenuItems)
                binding.executorDropMenuAutoCompleteTextView.setAdapter(executorArrayAdapter)
                binding.executorDropMenuAutoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                    lifecycleScope.launch {
                        viewModelUser.dataUser.collectLatest {
                            executorId = it[position].id
                        }
                    }
                }
            }
        }

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
            DatePickerDialog(
                this.requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                    Calendar.MONTH
                ), myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        /* TimePickerDialog */
        val timePicker = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTimeLabel(myCalendar)
        }

        vTimePicker.setOnClickListener {
            TimePickerDialog(
                this.requireContext(),
                timePicker,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(
                    Calendar.MINUTE
                ),
                true
            ).show()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleTextInputLayout.editText?.text.toString()
            val planExecuteDateInPicker =
                binding.dateInPlanTextInputLayout.editText?.text.toString()
            val planExecuteTimeInPicker =
                binding.timeInPlanTextInputLayout.editText?.text.toString()
            val description = binding.descriptionTextInputLayout.editText?.text.toString()
            val planExecuteDate =

            if (binding.executorDropMenuAutoCompleteTextView.text.isNotBlank() && executorId == null) {
                Toast.makeText(this.context, "Выбери исполнителя из списка", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (title.isNotBlank() &&
                planExecuteDateInPicker.isNotBlank() &&
                planExecuteTimeInPicker.isNotBlank() &&
                description.isNotBlank()
            ) {
//                val planExecuteDate = saveDateTime(planExecuteDateInPicker, planExecuteTimeInPicker)
//                val dateNow =

                viewModelClaim.updateClaim(
                    Claim(
                    title = title,
                    creator = creatorId,
                    executor = executorId,
                    planExecuteDate = saveDateTime(
                        planExecuteDateInPicker,
                        planExecuteTimeInPicker
                    ),
                    description = description
                )
                )
            }

            TODO("Добавить обработку чекбоксов")
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