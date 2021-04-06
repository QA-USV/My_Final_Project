package ru.netology.fmhandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.fmhandroid.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}