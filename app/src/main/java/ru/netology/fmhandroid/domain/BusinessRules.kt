package ru.netology.fmhandroid.domain

import ru.netology.fmhandroid.enum.ExecutionPriority
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class BusinessRules {

    companion object {

        /**
         * Функция определяющая и возвращающая уровень приоритета выполнения записок
         */
        fun determiningPriorityLevelOfNote(
            /**
             * Аргумент, текущее время
             */
            currentDateTime: LocalDateTime,
            /**
             * Аргумент, плановая дата выполнения записки
             */
            planeDateTime: LocalDateTime
        ): ExecutionPriority {

            /**
             * Разница между текущим временем и плановой датой выпонения записки, в минутах
             */
            val hoursToMinutes: Long = ChronoUnit.MINUTES.between(currentDateTime, planeDateTime)
            /**
             * Если разница между текущим временем и плановой датой выпонения записки
             * меньше 120 минут, то возвращаем высокий уровень приоритета
             */
            if (hoursToMinutes <= 120) {
                return ExecutionPriority.HIGH
            }
            /**
             * Ближайшие 6.00 (утра), если сейчас меньше 6ти, выставляем текущий день 6ть утра
             */
            val nextSixOClock = LocalDateTime.of(
                currentDateTime.year,
                currentDateTime.monthValue,
                currentDateTime.dayOfMonth,
                6,
                0
            )
            /**
             * Если сегодня уже больше 6ти утра, то выставляем на 6ть утра следующего дня
             */
            if (currentDateTime.hour > 6) {
                nextSixOClock.plusDays(1)
            }
            /**
             *  Если разница между текущим временем и плановой датой выпонения записки > 120 минут
             *  и плановая дата выполнения раньше чем ближайшие 6ть утра выставляем
             *  средний уровень приоритета выполнения записки
             */
            if (hoursToMinutes > 120 && planeDateTime.isBefore(nextSixOClock)) {
                return ExecutionPriority.MEDIUM
            }
            /**
             * Если ни одно из вышеперечисленных условий не выполнено, то выставляем низкий уровень
             * приоритета выполнения записки
             */
            return ExecutionPriority.LOW
        }
    }
}