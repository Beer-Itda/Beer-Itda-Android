package com.hjiee.presentation.util.ext

import kotlinx.coroutines.flow.*


object EventFlow {

    private val events = MutableSharedFlow<GlobalEvent>()
    val mutableEvents = events.asSharedFlow()


    suspend fun post(event: GlobalEvent) {
        events.emit(event)
    }

    inline fun <reified T> subscribe(): Flow<T> {
        return mutableEvents.filter { it is T }.map { it as T }
    }

}

sealed class GlobalEvent {
    data class Favorite(val beerId: Int) : GlobalEvent()
}