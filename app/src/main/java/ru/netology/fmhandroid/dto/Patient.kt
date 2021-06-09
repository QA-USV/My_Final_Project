package ru.netology.fmhandroid.dto

import com.google.gson.annotations.SerializedName

data class Patient(
    val id: Int,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("middleName")
    var middleName: String,
    @SerializedName("birthDate")
    val birthDate: String,
    val currentAdmissionId: Int? = null,
    val deleted: Boolean = false,
    val status: PatientStatusEnum? = null,
    @SerializedName("shortPatientName")
    val shortPatientName: String
)

enum class PatientStatusEnum {
    ACTIVE,
    DISCHARGED,
    EXPECTED
}
