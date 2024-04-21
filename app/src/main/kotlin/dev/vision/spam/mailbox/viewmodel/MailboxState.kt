package dev.vision.spam.mailbox.viewmodel

import androidx.compose.runtime.Immutable
import dev.vision.spam.mailbox.model.Email
import dev.vision.spam.mailbox.model.Inbox
import dev.vision.spam.mailbox.model.Message

@Immutable
data class MailboxState(
    val loading: Boolean = false,
    val inboxes: List<Inbox> = emptyList(),
    val messages: List<Message> = emptyList(),
    val spam: Map<Message, Float> = emptyMap(),
    val ham: Map<Message, Float> = emptyMap(),
)