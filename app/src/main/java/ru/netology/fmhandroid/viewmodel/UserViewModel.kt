package ru.netology.fmhandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.netology.fmhandroid.repository.userRepository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun getAllUsers() {
        TODO("Not yet implemented")
    }

    suspend fun saveUser() {
        TODO("Not yet implemented")
    }

    suspend fun updateUser() {
        TODO("Not yet implemented")
    }

    suspend fun getUserById() {
        TODO("Not yet implemented")
    }
}