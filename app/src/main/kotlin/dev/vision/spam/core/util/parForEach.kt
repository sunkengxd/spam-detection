package dev.vision.spam.core.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

suspend fun <T, R> Iterable<T>.parMap(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend (T) -> R
): List<R> =
    this.map {
        coroutineScope {
            async(context) {
                block(it)
            }
        }
    }.awaitAll()