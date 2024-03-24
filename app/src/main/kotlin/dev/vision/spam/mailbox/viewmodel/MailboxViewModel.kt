package dev.vision.spam.mailbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vision.spam.classifier.SpamClassification
import dev.vision.spam.classifier.SpamClassifier
import dev.vision.spam.mailbox.repository.EmailRepository
import dev.vision.spam.core.util.parForEach
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

    fun sort(by: EmailSort) = viewModelScope.launch {
        update {
            copy(
                emails = when (by) {
                    EmailSort.AtoZ -> emails.sortedBy { it.topic }
                    EmailSort.SpamFirst -> (spam + ham).toList()
                    EmailSort.HamFirst -> (ham + spam).toList()
                }
            )
        }
    }

    fun load() = viewModelScope.launch {
        val emails = repository.fetch()
        update {
            copy(emails = emails)
        }
        emails.parForEach(viewModelScope) { email ->
            val result = classifier.check(email.content)
            update {
                if (result == SpamClassification.Ham) {
                    copy(ham = ham + email)
                } else {
                    copy(spam = spam + email)
                }
            }
        }
    }
}
