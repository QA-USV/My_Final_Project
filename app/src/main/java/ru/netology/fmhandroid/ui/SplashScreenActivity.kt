package ru.netology.fmhandroid.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val splashscreenImages = listOf(
        R.drawable.image_splashscreen_1 to "Любая помощь важна и нужна",
        R.drawable.image_splashscreen_2 to "Хоспис – это компетентная помощь и любовь к пациентам",
        R.drawable.image_splashscreen_3 to "Творческий и осознанный подход к жизни до конца",
        R.drawable.image_splashscreen_4 to "Ответственно и осознанно нести добро людям",
        R.drawable.image_splashscreen_5 to "Помощь – это создание комфорта для пациентов и их близких",
        R.drawable.image_splashscreen_6 to "Хоспис – это наука помощи и искусство ухода",
        R.drawable.image_splashscreen_7 to "Творческий и осознанный подход к жизни пациента",
        R.drawable.image_splashscreen_8 to "Добро есть везде и во всех",
        R.drawable.image_splashscreen_9 to "Ответственная доброта",
        R.drawable.image_splashscreen_10 to "Создание физического и психологического пространства для завершения жизни",
        R.drawable.image_splashscreen_11 to "Творческий и осознанный подход к жизни пациента",
        R.drawable.image_splashscreen_12 to "Чем больше мы принимаем добра, тем больше отдаем",
        R.drawable.image_splashscreen_13 to "Хоспис – это воплощенная гуманность",
        R.drawable.image_splashscreen_14 to "Хоспис — это призвание и служение человечеству",
        R.drawable.image_splashscreen_15 to "Хоспис – это наука помощи и искусство ухода",
        R.drawable.image_splashscreen_16 to "Ответственно и осознанно нести добро людям",
        R.drawable.image_splashscreen_17 to "Хоспис – это компетентная помощь и любовь к пациентам",
    )
    private val splashscreenImage = splashscreenImages.random()

    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(binding.root)

        binding.splashscreenImageView.setBackgroundResource(splashscreenImage.first)
        binding.splashscreenTextView.text = splashscreenImage.second

        Handler().postDelayed({
            startActivity(Intent(this, AppActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 3000)
    }

    private fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }
}