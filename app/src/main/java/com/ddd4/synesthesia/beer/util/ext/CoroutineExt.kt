package com.ddd4.synesthesia.beer.util.ext

import com.hjiee.core.util.log.L
import kotlinx.coroutines.*

suspend fun <T> safeBlock(
    block: suspend () -> T
): T? {
    return try {
        block.invoke()
    } catch (e: Exception) {
        L.e(e)
        null
    }
}

fun <T> CoroutineScope.safeAsync(
    block: suspend () -> T
): Deferred<T?> {
    return async {
        safeBlock(block)
    }
}

fun <T> CoroutineScope.safeLaunch(
    block: suspend () -> T
): Job {
    return launch {
        safeBlock(block)
    }
}