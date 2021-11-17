package ru.netology.fmhandroid.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
import ru.netology.fmhandroid.databinding.FragmentFilterNewsBinding
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.utils.Utils.saveDateTime
import ru.netology.fmhandroid.utils.Utils.updateDateLabel
import ru.netology.fmhandroid.viewmodel.NewsControlPanelViewModel
import ru.netology.fmhandroid.viewmodel.NewsViewModel
import java.util.*

@AndroidEntryPoint
class FilterNewsListFragment : Fragment(R.layout.fragment_filter_news) {
    private lateinit var binding: FragmentFilterNewsBinding
    private lateinit var vPublishDateStartPicker: TextInputEditText
    private lateinit var vPublishDateEndPicker: TextInputEditText
    private val newsListViewModel: NewsViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val newsControlPanelViewModel: NewsControlPanelViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val args: FilterNewsListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilterNewsBinding.bind(view)

        val parentFragmentId: Int = args.backStackTagArgs

        lifecycleScope.launch {
            newsListViewModel.getAllNewsCategories().collect {
                val newsCategoryItems = it

                val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, newsCategoryItems)
                binding.newsItemCategoryTextAutoCompleteTextView.setAdapter(adapter)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            newsListViewModel.loadNewsCategoriesExceptionEvent.collect {
                val activity = activity ?: return@collect
                val dialog = android.app.AlertDialog.Builder(activity)
                dialog.setMessage(R.string.error)
                    .setPositiveButton(R.string.fragment_positive_button) { alertDialog, _ ->
                        alertDialog.cancel()
                    }
                    .create()
                    .show()
            }
        }

        val calendar = Calendar.getInstance()

        vPublishDateStartPicker = binding.newsItemPublishDateStartTextInputEditText

        val publishDateStartPicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateLabel(calendar, vPublishDateStartPicker)
            }

        vPublishDateStartPicker.setOnClickListener {
            DatePickerDialog(
                this.requireContext(),
                publishDateStartPicker,
                calendar.get(Calendar.YEAR),
                calendar.get(
                    Calendar.MONTH
                ),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        vPublishDateEndPicker = binding.newsItemPublishDateEndTextInputEditText

        val publishDateEndPicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateLabel(calendar, vPublishDateEndPicker)
            }

        vPublishDateEndPicker.setOnClickListener {
            DatePickerDialog(
                this.requireContext(),
                publishDateEndPicker,
                calendar.get(Calendar.YEAR),
                calendar.get(
                    Calendar.MONTH
                ),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        var category: String? = null
        binding.newsItemCategoryTextAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            category = if (position >= 0) parent.getItemAtPosition(position).toString() else null
        }

        var dates: List<Long>? = null
        binding.filterButton.setOnClickListener {
            if (vPublishDateStartPicker.text.toString().isNotBlank() &&
                vPublishDateEndPicker.text.toString().isNotBlank()
            ) {
                dates = listOf(
                    saveDateTime(vPublishDateStartPicker.text.toString(), "00:00"),
                    saveDateTime(vPublishDateEndPicker.text.toString(), "23:59")
                )
                navigateUp(parentFragmentId, category, dates)

            } else if (vPublishDateStartPicker.text.toString().isNotBlank() &&
                vPublishDateEndPicker.text.isNullOrBlank() ||
                vPublishDateStartPicker.text.isNullOrBlank() &&
                vPublishDateEndPicker.text.toString().isNotBlank()
            ) {
                val activity = activity ?: return@setOnClickListener
                val dialog = android.app.AlertDialog.Builder(activity)
                dialog.setMessage(R.string.wrong_news_date_period)
                    .setPositiveButton(R.string.fragment_positive_button) { alertDialog, _ ->
                        alertDialog.cancel()
                    }
                    .create()
                    .show()

            } else navigateUp(parentFragmentId, category, dates)
        }


        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateUp(parentFragmentId: Int, category: String?, dates: List<Long>?) {
        val categoryId: Int? = if (category != null) Utils.convertNewsCategory(category) else null
        if (parentFragmentId == R.id.newsControlPanelFragment) {
            newsControlPanelViewModel.onFilterNewsClicked(
                categoryId,
                dateStart = dates?.get(0),
                dateEnd = dates?.get(1)
            )
        } else {
            newsListViewModel.onFilterNewsClicked(
                categoryId,
                dateStart = dates?.get(0),
                dateEnd = dates?.get(1)
            )
        }
//        val newsFilterArgs = NewsFilterArgs(
//            category,
//            dates
//        )
//        setFragmentResult("requestKey", bundleOf("filterArgs" to newsFilterArgs))
        findNavController().navigateUp()
    }
}
