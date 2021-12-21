package ru.netology.fmhandroid.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.viewmodel.AuthViewModel
import ru.netology.fmhandroid.viewmodel.NewsViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        val newsViewModel: NewsViewModel by viewModels()
        val authViewModel: AuthViewModel by viewModels()

        val categories =
            listOf(
                News.Category(1, "Объявление", false),
                News.Category(2, "День рождения", false),
                News.Category(3, "Зарплата", false),
                News.Category(4, "Профсоюз", false),
                News.Category(5, "Праздник", false),
                News.Category(6, "Массаж", false),
                News.Category(7, "Благодарность", false),
                News.Category(8, "Нужна помощь", false)
            )

        newsViewModel.initializationListNewsCategories(categories)

        lifecycleScope.launch {
            authViewModel.serverErrorStateFlow.collectLatest {
                val loginFragment = supportFragmentManager.findFragmentById(R.id.authFragment)
                supportFragmentManager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                if (loginFragment != null) {
                    supportFragmentManager.beginTransaction()
                        .add(loginFragment, "login")
                        .commit()
                }
            }
        }

    }
}
