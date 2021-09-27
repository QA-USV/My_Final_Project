package ru.netology.fmhandroid.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Patient(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val middleName: String? = null,
    val birthDate: Long? = null,
    val currentAdmissionId: Int? = null,
    val deleted: Boolean = false,
): Parcelable {
    enum class Status {
        ACTIVE,
        EXPECTED,
        DISCHARGED
    }
}