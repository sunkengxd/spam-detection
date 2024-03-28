package dev.vision.spam.mailbox.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import dev.vision.spam.classifier.SpamClassification
import dev.vision.spam.mailbox.viewmodel.MailboxState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageList(state: MailboxState, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = spacedBy(10.dp),
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
        items(
            state.messages,
            key = { it.subject }
        ) { email ->
            MessageItem(
                message = email,
                classification = when (email) {
                    in state.spam -> SpamClassification.Spam
                    in state.ham -> SpamClassification.Ham
                    else -> null
                },
                modifier = Modifier
                    .animateItemPlacement()
                    .shadow(5.dp, shapes.extraSmall)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        colorScheme.surfaceContainer,
                        shapes.extraSmall
                    )
            )
        }
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}
