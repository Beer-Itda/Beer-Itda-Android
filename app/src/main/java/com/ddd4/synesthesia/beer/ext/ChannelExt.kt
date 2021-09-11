package com.ddd4.synesthesia.beer.ext

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.ui.common.beer.item.BeerItemViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.*

@ExperimentalCoroutinesApi
object CoroutinesEvent {

    private val observerChanner = BroadcastChannel<Any>(Channel.BUFFERED)

    suspend fun publish(o: Any) {
        observerChanner.send(o)
    }

    fun <T> listen(eventType: Class<T>): ReceiveChannel<T> =
        observerChanner.openSubscription().filter { it is kotlin.Any }.map { it as T }
}

sealed class ChannelType {
    data class Favorite(val beer: BeerItemViewModel?) : ChannelType()
}