package dev.vision.spam.mailbox.repository

import dev.vision.spam.mailbox.model.Email

interface EmailRepository {

    suspend fun fetch(): List<Email>
}