package dev.vision.spam.mailbox.viewmodel

import androidx.compose.runtime.Immutable
import dev.vision.spam.mailbox.model.Email

@Immutable
data class MailboxState(
    val emails: List<Email> = emptyList(),
    val spam: Set<Email> = emptySet(),
    val ham: Set<Email> = emptySet(),
)