package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.NewsListAdapter
import ru.netology.fmhandroid.adapter.NewsOnInteractionListener
import ru.netology.fmhandroid.databinding.FragmentNewsListBinding
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.User
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    private lateinit var binding: FragmentNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsListBinding.bind(view)

        val adapter = NewsListAdapter()

//        val newsItem1 = News(
//            1,
//            News.Category.Birthday(),
//            "День рождения Дяди Паши!",
//            "У Дяди Паши день рождения! 12.09.2021г. в 16:00 будет сумасшедшая вечеринка с барбекю и холодным чаем! Всех ждем!",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.now(),
//            LocalDateTime.now(),
//            false,
//            true
//        )
//
//        val newsItem2 = News(
//            2,
//            News.Category.Advertisement(),
//            "Субботник 11 сентября!",
//            "11 сентября на территории хосписа проводится общий субботник! Надеемся на максимальное присутсвие как сотрудников, так и волонтеров!",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.now(),
//            LocalDateTime.now(),
//            true,
//            true
//        )
//
//        val newsItem3 = News(
//            2,
//            News.Category.Help(),
//            "Нужна помощь для подготовки к празднику!",
//            "Нужны волонтеры для помощи в подготовке ко дню медицинского работника",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            false,
//            true
//        )
//
//        val newsItem4 = News(
//            3,
//            News.Category.Massage(),
//            "Балийский массаж",
//            "Массаж стоп от профессиональных балинизийских массажисток! Сеансы: пн, пт в 17:00, ср в 16:00. Запись на посту № 2",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            LocalDateTime.of(2021, 8, 25, 15, 55),
//            true,
//            true
//        )
//
//        val newsItem5 = News(
//            3,
//            News.Category.Holiday(),
//            "День медицинского работника",
//            "Празднование назначено на 22 июля в 16:00 в актовом зале. Специально для Вас выступит Надежда Кадышева и вакально-инструмантальный ансамбль Золотое Кольцо!",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            LocalDateTime.of(2021, 8, 25, 15, 55),
//            false,
//            true
//        )
//
//        val newsItem6 = News(
//            3,
//            News.Category.Salary(),
//            "Выдача зарплаты сотрудникам",
//            "31 августа выдача зарплаты сотрудникам. Не потратьте все сразу)",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            LocalDateTime.of(2021, 8, 25, 15, 55),
//            true,
//            true
//        )
//
//        val newsItem7 = News(
//            3,
//            News.Category.Union(),
//            "Профсоюзное собрание",
//            "6 сентября в 13:00 состоится профсоюзное собрание. Членам профсоюза быть в обязательном порядке!",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            LocalDateTime.of(2021, 8, 25, 15, 55),
//            true,
//            true
//        )
//
//        val newsItem8 = News(
//            3,
//            News.Category.Gratitude(),
//            "Объявление благодарностей",
//            "22 июля по случаю празднования дня медицинского работника состоится объявление благодарностей сотрудникам за труды!",
//            User(
//                1,
//                lastName = "Иванов",
//                firstName = "Сергей",
//                middleName = "Павлович",
//                deleted = false
//            ),
//            LocalDateTime.of(2021, 8, 25, 15, 47),
//            LocalDateTime.of(2021, 8, 25, 15, 55),
//            true,
//            true
//        )
//
//        val data: List<News> = listOf(
//            newsItem1,
//            newsItem2,
//            newsItem3,
//            newsItem4,
//            newsItem5,
//            newsItem6,
//            newsItem7,
//            newsItem8
//        )
//
//        adapter.submitList(data)
//
////        lifecycleScope.launchWhenCreated {
////            viewModel.data
////                .collectLatest { state ->
////                    adapter.submitList(state)
////                    binding.emptyText.isVisible = state.isEmpty()
////                }
////        }
//
//        binding.newsListRecyclerView.adapter = adapter
//
//        binding.apply {
//            informationImageView.setOnClickListener {
//                when (newsHelpersGroup.visibility) {
//                    View.GONE -> showHelper()
//                    else -> hideHelper()
//                }
//            }
//
//            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//                when (newsHelpersGroup.visibility) {
//                    View.VISIBLE -> hideHelper()
//                    else -> findNavController().popBackStack()
//                }
//            }
//        }
//        viewModel.loadPatientExceptionEvent.observe(viewLifecycleOwner, {
//            val activity = activity ?: return@observe
//            val dialog = activity.let { activity ->
//                AlertDialog.Builder(activity)
//            }
//            dialog.setMessage(R.string.error_loading)
//                .setPositiveButton(R.string.fragment_positive_button) { dialog, _ ->
//                    dialog.cancel()
//                }
//                .create()
//                .show()
//        })
    }

    private fun showHelper() {
        binding.apply {
            newsListLayout.setBackgroundResource(R.color.news_helper_title_background)
            titleBackgroundImageView.setBackgroundResource(R.color.news_helper_title_background)
            newsHelpersGroup.visibility = View.VISIBLE
        }
    }

    private fun hideHelper() {
        binding.apply {
            newsListLayout.setBackgroundResource(R.color.transparent)
            titleBackgroundImageView.setBackgroundResource(R.color.light_grey)
            newsHelpersGroup.visibility = View.GONE
        }
    }
}