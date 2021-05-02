package ru.netology.fmhandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.fmhandroid.R

class main_activity : AppCompatActivity(R.layout.patient_profile_card) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_profile_card)
    }
}