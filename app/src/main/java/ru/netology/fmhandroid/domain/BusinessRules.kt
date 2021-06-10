package ru.netology.fmhandroid.domain

import android.os.Build
import androidx.annotation.RequiresApi
import ru.netology.fmhandroid.enum.ExecutionPriority
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class BusinessRules {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun determiningPriorityLevelOfNote(
            currentDateTime: LocalDateTime,
            planeDateTime: LocalDateTime
        ): ExecutionPriority {

            val hoursToMinutes: Long = ChronoUnit.MINUTES.between(currentDateTime, planeDateTime)
            if (hoursToMinutes <= 120) {
                return ExecutionPriority.HIGH
            }

            val nextSixOClock = LocalDateTime.of(
                currentDateTime.year,
                currentDateTime.monthValue,
                currentDateTime.dayOfMonth,
                6,
                0
            )

            if (currentDateTime.hour > 6) {
                nextSixOClock.plusDays(1)
            }

            if (hoursToMinutes > 120 && planeDateTime.isBefore(nextSixOClock)) {
                return ExecutionPriority.MEDIUM
            }

            return ExecutionPriority.LOW
        }
    }
}