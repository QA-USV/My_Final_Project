package ru.netology.fmhandroid.utils

import ru.netology.fmhandroid.dto.Note
import ru.netology.fmhandroid.dto.Patient
import ru.netology.fmhandroid.dto.PatientStatusEnum
import java.time.LocalDateTime
import java.time.ZoneId

class Utils {
    companion object {
        val emptyPatient = Patient(
            id = 0,
            firstName = "",
            lastName = "",
            middleName = "",
            birthDate = "",
            currentAdmissionId = 0,
            deleted = false,
            status = PatientStatusEnum.EXPECTED,
            shortPatientName = ""
        )

        val emptyNote = Note(
            id = 0,
            patientId = null,
            description = "",
            creatorId = null,
            executorId = null,
            createDate = null,
            planeExecuteDate = LocalDateTime.now(),
            factExecuteDate = null,
            noteStatus = null,
            comment = null,
            deleted = false,
            shortExecutorName = "",
            shortPatientName = ""
        )

        fun LocalDateTime.toMillis(zone: ZoneId = ZoneId.systemDefault()) = atZone(zone)?.toInstant()?.toEpochMilli()
    }
}