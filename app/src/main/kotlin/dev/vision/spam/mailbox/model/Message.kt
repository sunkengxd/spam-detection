package dev.vision.spam.mailbox.model

data class Message(
    val from: User,
    val subject: String,
    val body: String,
)
