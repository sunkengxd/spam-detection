package dev.vision.spam.mailbox.model

data class Email(
    val from: String,
    val topic: String,
    val content: String
)