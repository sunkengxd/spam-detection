package dev.vision.spam.mailbox.api

import android.content.Context
import android.util.Log
import dev.vision.spam.Cache
import dev.vision.spam.mailbox.model.Inbox
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class InboxDto(
    val id: Long,
    val name: String,
    val username: String,
    val password: String,
    @SerialName("max_size")
    val maxSize: Int,
    val status: String,
    @SerialName("email_username")
    val emailUsername: String,
    @SerialName("email_username_enabled")
    val emailUsernameEnabled: Boolean,
    @SerialName("sent_messages_count")
    val sentMessagesCount: Int,
    @SerialName("forwarded_messages_count")
    val forwardedMessagesCount: Int,
    val used: Boolean,
    @SerialName("forward_from_email_address")
    val forwardFromEmailAddress: String,
    @SerialName("project_id")
    val projectId: Int,
    val domain: String,
    @SerialName("pop3_domain")
    val pop3Domain: String,
    @SerialName("email_domain")
    val emailDomain: String,
    @SerialName("smtp_ports")
    val smtpPorts: List<Int>,
    @SerialName("pop3_ports")
    val pop3Ports: List<Int>,
    @SerialName("emails_count")
    val emailsCount: Int,
    @SerialName("emails_unread_count")
    val emailsUnreadCount: Int,
    @SerialName("last_message_sent_at")
    val lastMessageSentAt: String,
    @SerialName("max_message_size")
    val maxMessageSize: Int,
    val permissions: Permissions
) {

    @Serializable
    class Permissions(
        @SerialName("can_read")
        val canRead: Boolean,
        @SerialName("can_update")
        val canUpdate: Boolean,
        @SerialName("can_destroy")
        val canDestroy: Boolean,
        @SerialName("can_leave")
        val canLeave: Boolean
    )
}

fun InboxDto.toDomain() = Inbox(
    id = id, name = name
)

fun client(cache: Cache) = HttpClient {

    expectSuccess = true

    val key = cache.getKey()

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "mailtrap.io/api"
//                path("/api/")
            header("Api-Token", key)
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }

    install(Logging) {
        logger = object: Logger {
            override fun log(message: String) {
                Log.d("mailtrap", message)
            }
        }
    }
}
