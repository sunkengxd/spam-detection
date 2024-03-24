package dev.vision.spam.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun <E> Collection<E>.parForEach(
    scope: CoroutineScope,
    context: CoroutineContext = Dispatchers.Default,
    block: suspend (E) -> Unit
) = this.forEach {
    scope.launch(context) { block(it) }
}
