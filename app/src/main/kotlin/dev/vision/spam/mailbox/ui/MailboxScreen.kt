package dev.vision.spam.mailbox.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.vision.spam.mailbox.viewmodel.MailboxViewModel

@Composable
fun MailboxScreen(viewModel: MailboxViewModel, modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    EmailList(
        state = state,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .then(modifier)
    )
}