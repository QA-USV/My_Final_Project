package ru.netology.fmhandroid.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentCreateEditClaimBinding
import ru.netology.fmhandroid.dto.Claim
import ru.netology.fmhandroid.dto.User
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.utils.Utils.fullUserNameGenerator
import ru.netology.fmhandroid.utils.Utils.saveDateTime
import ru.netology.fmhandroid.utils.Utils.updateDateLabel
import ru.netology.fmhandroid.utils.Utils.updateTimeLabel
import ru.netology.fmhandroid.viewmodel.ClaimCardViewModel
import ru.netology.fmhandroid.viewmodel.ClaimViewModel
import ru.netology.fmhandroid.viewmodel.UserViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@AndroidEntryPoint
class CreateEditClaimFragment : Fragment(R.layout.fragment_create_edit_claim) {
    private lateinit var vDatePicker: TextInputEditText
    private lateinit var vTimePicker: TextInputEditText
    private lateinit var binding: FragmentCreateEditClaimBinding
    private val args: CreateEditClaimFragmentArgs by navArgs()
    private var executor: User? = null

    //временно, пока нет авторизации
    private val creatorId = 1

    private val claimViewModel: ClaimViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val claimCardViewModel: ClaimCardViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val userViewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            claimViewModel.createClaimExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }
        lifecycleScope.launch {
            claimViewModel.claimCreatedEvent.collect {
                findNavController().navigateUp()
            }
        }
        lifecycleScope.launch {
            claimCardViewModel.claimUpdatedEvent.collect {
                findNavController().navigateUp()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateEditClaimBinding.bind(view)

        with(binding) {
            containerCustomAppBarIncludeOnFragmentCreateEditClaim.mainMenuImageButton.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentCreateEditClaim.authorizationImageButton.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentCreateEditClaim.ourMissionImageButton.visibility =
                View.GONE
            if (args.argClaim == null) {
                containerCustomAppBarIncludeOnFragmentCreateEditClaim.customAppBarTitleTextView.apply {
                    setText(R.string.creating)
                    textSize = 18F
                }
                containerCustomAppBarIncludeOnFragmentCreateEditClaim.customAppBarSubTitleTextView
                    .setText(R.string.claims)
            } else {
                containerCustomAppBarIncludeOnFragmentCreateEditClaim.customAppBarTitleTextView.apply {
                    setText(R.string.editing)
                    textSize = 18F
                }
                containerCustomAppBarIncludeOnFragmentCreateEditClaim.customAppBarSubTitleTextView
                    .setText(R.string.claims)
            }

            args.argClaim?.let { claim ->
                titleTextInputLayout.editText?.setText(claim.claim.title)
                dateInPlanTextInputLayout.editText?.setText(
                    claim.claim.planExecuteDate?.let { Utils.showDate(it) }
                )
                timeInPlanTextInputLayout.editText?.setText(
                    claim.claim.planExecuteDate?.let { Utils.showTime(it) }
                )
                descriptionTextInputLayout.editText?.setText(claim.claim.description)

                // реализовать работу с чекбоксами видимости
            }

            cancelButton.setOnClickListener {
                val activity = activity ?: return@setOnClickListener
                val dialog = AlertDialog.Builder(activity)
                dialog.setMessage(R.string.cancellation)
                    .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                        dialog.dismiss()
                        findNavController().popBackStack()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }

            saveButton.setOnClickListener {
                val activity = activity ?: return@setOnClickListener
                val dialog = android.app.AlertDialog.Builder(activity)
                if (titleTextInputLayout.editText?.text.isNullOrBlank() ||
                    dateInPlanTextInputLayout.editText?.text.isNullOrBlank() ||
                    timeInPlanTextInputLayout.editText?.text.isNullOrBlank() ||
                    descriptionTextInputLayout.editText?.text.isNullOrBlank()
                ) {
                    dialog.setMessage(R.string.empty_fields)
                        .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                            dialog.cancel()
                        }
                        .create()
                        .show()
                } else {
                    when (args.argClaim) {
                        null -> {
                            claimViewModel.save(fillClaim())
                        }
                        else -> {
                            claimCardViewModel.updateClaim(fillClaim())
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            userViewModel.dataUser.collectLatest {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.menu_item,
                    it.map { user ->
                        fullUserNameGenerator(
                            user.lastName!!,
                            user.firstName!!,
                            user.middleName!!
                        )
                    })
                binding.executorDropMenuAutoCompleteTextView.apply {
                    setAdapter(adapter)
                    setOnItemClickListener { _, _, position, _ ->
                        executor = it[position]
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
            updateDateLabel(myCalendar, vDatePicker)
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
            updateTimeLabel(myCalendar, vTimePicker)
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
    }

    private fun fillClaim(): Claim {
        with(binding) {
            return Claim(
                id = args.argClaim?.claim?.id,
                title = titleTextInputLayout.editText?.text.toString(),
                description = descriptionTextInputLayout.editText?.text.toString(),
                executorId = executor?.id,
                createDate = args.argClaim?.claim?.createDate ?: LocalDateTime.now()
                    .toEpochSecond(ZoneId.of("Europe/Moscow").rules.getOffset(Instant.now())),
                //* Временное поле. Подлежит удалению после введения регистрации/аутентификации *
                creatorId = 1,
                //------------------------------------------------------------------------------//
                planExecuteDate = saveDateTime(
                    dateInPlanTextInputLayout.editText?.text.toString(),
                    timeInPlanTextInputLayout.editText?.text.toString()
                )
            )
        }
    }
    private fun showErrorToast(text: Int) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}