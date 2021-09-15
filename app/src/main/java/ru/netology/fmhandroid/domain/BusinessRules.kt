package ru.netology.fmhandroid.domain

import ru.netology.fmhandroid.enum.ExecutionPriority
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object BusinessRules {

    // Функция определяющая и возвращающая уровень приоритета выполнения записок
    fun determiningPriorityLevelOfNote(

        // Аргумент, текущее время
        currentDateTime: LocalDateTime,

        //Аргумент, плановая дата выполнения записки
        planeDateTime: LocalDateTime
    ): ExecutionPriority {

        val differenceBetweenCurrentAndPlannedTime: Long = ChronoUnit.MINUTES.between(
            currentDateTime,
            planeDateTime
        )

        /*
         * Если разница между текущим временем и плановой датой выпонения записки
         * меньше 120 минут, то возвращаем высокий уровень приоритета
         */
        if (differenceBetweenCurrentAndPlannedTime <= 120) {
            return ExecutionPriority.HIGH
        }

        // Ближайшие 6.00 (утра), если сейчас меньше 6ти, выставляем текущий день 6ть утра
        var nextSixOClock = LocalDateTime.of(
            currentDateTime.year,
            currentDateTime.monthValue,
            currentDateTime.dayOfMonth,
            6,
            0
        )

        // Если сегодня уже больше 6ти утра, то выставляем на 6ть утра следующего дня
        if (currentDateTime.hour > 6) {
            nextSixOClock = nextSixOClock.plusDays(1)
        }

        /*
         *  Если разница между текущим временем и плановой датой выпонения записки > 120 минут
         *  и плановая дата выполнения раньше чем ближайшие 6ть утра выставляем
         *  средний уровень приоритета выполнения записки
         */
        if (differenceBetweenCurrentAndPlannedTime > 120 && planeDateTime.isBefore(nextSixOClock)) {
            return ExecutionPriority.MEDIUM
        }

        /*
         * Если ни одно из вышеперечисленных условий не выполнено, то выставляем низкий уровень
         * приоритета выполнения записки
         */
        return ExecutionPriority.LOW
    }
}
