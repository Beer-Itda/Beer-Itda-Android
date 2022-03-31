package com.ddd4.synesthesia.beer.util.ext

import com.hjiee.core.util.log.L
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

suspend inline fun <reified T> safeAsync(
    coroutineScope: CoroutineScope,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend () -> T
): Deferred<T?> {
    return coroutineScope.async(coroutineContext) {
        try {
            block.invoke()
        } catch (e: Exception) {
            L.e(e)
            null
        }
    }
}

suspend inline fun <reified T> safeLaunch(
    coroutineScope: CoroutineScope,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend () -> T
): Job {
    return coroutineScope.launch(coroutineContext) {
        try {
            block.invoke()
        } catch (e: Exception) {
            L.e(e)
        }
    }
}