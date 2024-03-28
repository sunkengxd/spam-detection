package dev.vision.spam.core.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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

suspend fun <T, R> Iterable<T>.parForEach(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend (T) -> R
) =
    this.forEach {
        coroutineScope {
            launch(context) {
                block(it)
            }
        }
    }