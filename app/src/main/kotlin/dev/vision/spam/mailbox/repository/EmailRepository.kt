package dev.vision.spam.mailbox.repository

import dev.vision.spam.mailbox.model.Inbox
import dev.vision.spam.mailbox.model.Message

interface EmailRepository {

    suspend fun inboxes(): List<Inbox>
    suspend fun messages(inbox: Inbox): List<Message>
}