package dev.vision.spam.mailbox.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("Unused")
@Serializable
class AccountDto(
    val id: Long,
    val name: String,
    @SerialName("access_levels")
    val accessLevels: List<Int>
)
