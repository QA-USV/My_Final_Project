package ru.netology.fmhandroid.repository

interface FmhRepository {
    fun getNews(): List<News>
    fun getPatients(): List<Patient>
    fun getNotes(id: Long): List<Note>
}