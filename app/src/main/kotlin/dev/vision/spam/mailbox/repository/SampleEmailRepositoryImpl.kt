package dev.vision.spam.mailbox.repository

import dev.vision.spam.mailbox.model.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class SampleEmailRepositoryImpl() : EmailRepository {

    private val mailbox = List(50) { i ->
        Email(from = "Somebody", content = "zxc", topic = "Topic #$i")
    }

    override suspend fun fetch() = withContext(Dispatchers.IO) {
        mailbox.take(10)
    }
}

fun emailRepository(): EmailRepository = SampleEmailRepositoryImpl()