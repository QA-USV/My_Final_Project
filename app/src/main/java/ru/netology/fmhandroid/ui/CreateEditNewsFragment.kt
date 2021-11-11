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
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.FragmentCreateEditNewsBinding
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.utils.Utils.convertNewsCategory
import ru.netology.fmhandroid.utils.Utils.saveDateTime
import ru.netology.fmhandroid.utils.Utils.updateDateLabel
import ru.netology.fmhandroid.utils.Utils.updateTimeLabel
import ru.netology.fmhandroid.viewmodel.NewsViewModel
import java.time.*
import java.time.Instant.now
import java.util.*

@AndroidEntryPoint
class CreateEditNewsFragment : Fragment(R.layout.fragment_create_edit_news) {
    private val viewModel: NewsViewModel by viewModels()
    private val args: CreateEditNewsFragmentArgs by navArgs()

    private lateinit var vPublishDatePicker: TextInputEditText
    private lateinit var vPublishTimePicker: TextInputEditText
    private lateinit var binding: FragmentCreateEditNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        lifecycleScope.launch {
            viewModel.saveNewsItemExceptionEvent.collect {
                showErrorToast(R.string.error_saving)
            }
        }
        lifecycleScope.launch {
            viewModel.editNewsItemExceptionEvent.collect {
                showErrorToast(R.string.error_saving)
            }
        }
        lifecycleScope.launch {
            viewModel.loadNewsCategoriesExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }
        lifecycleScope.launch {
            viewModel.newsItemCreatedEvent.collect {
                findNavController().navigateUp()
            }
        }
        lifecycleScope.launch {
            viewModel.editNewsItemSavedEvent.collect {
                findNavController().navigateUp()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateEditNewsBinding.bind(view)

        with(binding) {
            containerCustomAppBarIncludeOnFragmentCreateEditNews.mainMenuImageButton.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentCreateEditNews.authorizationImageButton.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentCreateEditNews.ourMissionImageButton.visibility =
                View.GONE
            if (args.newsItemArg == null) {
                containerCustomAppBarIncludeOnFragmentCreateEditNews.customAppBarTitleTextView.apply {
                    setText(R.string.creating)
                    textSize = 18F
                }
                containerCustomAppBarIncludeOnFragmentCreateEditNews.customAppBarSubTitleTextView
                    .setText(R.string.news)
            } else {
                containerCustomAppBarIncludeOnFragmentCreateEditNews.customAppBarTitleTextView.apply {
                    setText(R.string.editing)
                    textSize = 18F
                }
                containerCustomAppBarIncludeOnFragmentCreateEditNews.customAppBarSubTitleTextView
                    .setText(R.string.news)
            }
            args.newsItemArg?.let { newsItem ->
                newsItemCategoryTextAutoCompleteTextView.setText(newsItem.news.category.name)
                newsItemTitleTextInputEditText.setText(newsItem.news.newsItem.title)
                newsItemPublishDateTextInputEditText.setText(
                    newsItem.news.newsItem.publishDate?.let { Utils.formatDate(it) }
                )
                newsItemPublishTimeTextInputEditText.setText(
                    newsItem.news.newsItem.publishDate?.let { Utils.formatTime(it) }
                )
                newsItemDescriptionTextInputEditText.setText(newsItem.news.newsItem.description)
                switcher.isChecked = newsItem.news.newsItem.publishEnabled
            }

            if (switcher.isChecked) {
                switcher.setText(R.string.news_item_active)
            } else {
                switcher.setText(R.string.news_item_not_active)
            }

            switcher.setOnClickListener {
                if (switcher.isChecked) {
                    switcher.setText(R.string.news_item_active)
                } else {
                    switcher.setText(R.string.news_item_not_active)
                }
            }

            cancelButton.setOnClickListener {
                val activity = activity ?: return@setOnClickListener
                val dialog = AlertDialog.Builder(activity)
                dialog.setMessage(R.string.cancellation)
                    .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
                        dialog.dismiss()
                        findNavController().navigateUp()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }

            saveButton.setOnClickListener {
                if (newsItemCategoryTextAutoCompleteTextView.text.isNullOrBlank() ||
                    newsItemTitleTextInputEditText.text.isNullOrBlank() ||
                    newsItemPublishDateTextInputEditText.text.isNullOrBlank() ||
                    newsItemPublishTimeTextInputEditText.text.isNullOrBlank() ||
                    newsItemDescriptionTextInputEditText.text.isNullOrBlank()
                ) {
                    showErrorToast(R.string.empty_fields)
                } else {
                    when (args.newsItemArg) {
                        null -> viewModel.save(fillNewsItem())
                        else -> viewModel.edit(fillNewsItem())
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getAllNewsCategories().collect {
                val newsCategoryItems = it

                with(binding) {
                    val adapter =
                        ArrayAdapter(requireContext(), R.layout.menu_item, newsCategoryItems)
                    newsItemCategoryTextAutoCompleteTextView.setAdapter(adapter)

                    newsItemCategoryTextAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position)
                        val title = binding.newsItemTitleTextInputEditText
                        newsCategoryItems.forEach { category ->
                            if (title.text.isNullOrBlank() || title.text.toString() == category.name) {
                                title.setText(selectedItem.toString())
                            }
                        }
                    }
                }
            }
        }

        val calendar = Calendar.getInstance()

        vPublishDatePicker = binding.newsItemPublishDateTextInputEditText

        val publishDatePicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateLabel(calendar, vPublishDatePicker)
            }

        vPublishDatePicker.setOnClickListener {
            DatePickerDialog(
                this.requireContext(),
                publishDatePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(
                    Calendar.MONTH
                ),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        vPublishTimePicker = binding.newsItemPublishTimeTextInputEditText

        val publishTimePicker = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            updateTimeLabel(calendar, vPublishTimePicker)
        }

        vPublishTimePicker.setOnClickListener {
            TimePickerDialog(
                this.requireContext(),
                publishTimePicker,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(
                    Calendar.MINUTE
                ),
                true
            ).show()
        }
    }

    private fun showErrorToast(text: Int) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun fillNewsItem(): News {
        with(binding) {
            return News(
                id = args.newsItemArg?.news?.newsItem?.id,
                title = newsItemTitleTextInputEditText.text.toString(),
                newsCategoryId = convertNewsCategory(
                    newsItemCategoryTextAutoCompleteTextView.text.toString()
                ),
                createDate = args.newsItemArg?.news?.newsItem?.createDate ?: LocalDateTime.now()
                    .toEpochSecond(ZoneId.of("Europe/Moscow").rules.getOffset(now())),
                //* Временное поле. Подлежит удалению после введения регистрации/аутентификации *
                creatorId = 1,
                //------------------------------------------------------------------------------//
                publishDate = saveDateTime(
                    newsItemPublishDateTextInputEditText.text.toString(),
                    newsItemPublishTimeTextInputEditText.text.toString()
                ),
                description = newsItemDescriptionTextInputEditText.text.toString(),
                publishEnabled = switcher.isChecked
            )
        }
    }
}