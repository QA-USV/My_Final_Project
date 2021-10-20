package ru.netology.fmhandroid.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Events{

    companion object {
        private val mutableEvents = MutableSharedFlow<Events>()
        val events = mutableEvents.asSharedFlow()

        suspend fun produceEvents(event: Events) {
            mutableEvents.emit(event)
        }
    }
}
