package ru.netology.fmhandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.fmhandroid.R

class MainActivity : AppCompatActivity(R.layout.fragment_patient_profile_card) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_patient_profile_card)
    }
}