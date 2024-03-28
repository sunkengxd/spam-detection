package dev.vision.spam.mailbox.repository

import dev.vision.spam.core.util.parMap
import dev.vision.spam.mailbox.api.AccountDto
import dev.vision.spam.mailbox.api.InboxDto
import dev.vision.spam.mailbox.api.MailtrapApi
import dev.vision.spam.mailbox.api.toDomain
import dev.vision.spam.mailbox.model.Inbox
import dev.vision.spam.mailbox.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MailtrapEmailRepository(
    private val api: MailtrapApi
) : EmailRepository {

    // cache
    private var accounts: List<AccountDto>? = null
    private var inboxes: List<Inbox>? = null

    // to not have to this@MaintralEmailRepository
    private val self = this

    override suspend fun inboxes(): List<Inbox> = withContext(Dispatchers.IO) {
        inboxes ?: (accounts ?: api.accounts()) // return inboxes if cached, otherwise get accounts
            .first() // fetch for first account
            .let { api.inboxes(it.id) } // do the fetch
            .map(InboxDto::toDomain) // transform each InboxDto to Inbox
            .also { this@MailtrapEmailRepository.inboxes = it } // also cache inboxes
    }

    override suspend fun messages(inbox: Inbox): List<Message> = withContext(Dispatchers.IO) {
        val account = (self.accounts ?: api.accounts()).first() // ensure accounts loaded
        val messages = api.messages(account.id, inbox.id) // fetch messages for first account
        messages.parMap { message ->
            api.body(account.id, inbox.id, message.id) // load bodies for each message in parallel
        }
            .zip(messages) // create Pair<String, MessageDto> for each message and it's body
            .map { (body, message) ->
                message.toDomain(body) // transform Pair<String, MessageDto> to Message
            }
    }
}
