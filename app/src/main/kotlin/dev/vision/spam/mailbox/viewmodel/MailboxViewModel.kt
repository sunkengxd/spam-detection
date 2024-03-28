package dev.vision.spam.mailbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vision.spam.classifier.SpamClassification
import dev.vision.spam.classifier.SpamClassifier
import dev.vision.spam.mailbox.model.Message
import dev.vision.spam.mailbox.repository.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class MailboxViewModel(
    private val repository: EmailRepository,
    private val classifier: SpamClassifier
) : ViewModel() {

    private val mutableState = MutableStateFlow(MailboxState())
    val state: StateFlow<MailboxState>
        get() = mutableState

    private inline fun update(block: MailboxState.() -> MailboxState) = mutableState.update(block)

    init {
        load()
    }

    fun sort(by: EmailSort) = viewModelScope.launch {
        update {
            copy(
                messages = when (by) {
                    EmailSort.AtoZ -> messages.sortedBy { it.subject }
                    EmailSort.SpamFirst -> (spam + ham).toList()
                    EmailSort.HamFirst -> (ham + spam).toList()
                }
            )
        }
    }

    fun load() = viewModelScope.launch {
        update { copy(loading = true) }
        val inboxes = repository.inboxes()
        update {
            copy(
                inboxes = inboxes,
                messages = emptyList(),
                spam = emptySet(),
                ham = emptySet()
            )
        }
        inboxes.parForEach { inbox ->
            val messages = repository.messages(inbox)
            update {
                copy(messages = messages)
            }
            messages.forEach(::processMessage)
        }
        update { copy(loading = false) }
    }

    private fun processMessage(message: Message) = viewModelScope.launch {
        val result = classifier.check(message.body)
        update {
            if (result == SpamClassification.Ham) {
                copy(ham = ham + message)
            } else {
                copy(spam = spam + message)
            }
        }
    }
}
