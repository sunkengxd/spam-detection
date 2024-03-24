package dev.vision.spam.mailbox.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.vision.spam.classifier.SpamClassification
import dev.vision.spam.classifier.SpamClassification.Ham
import dev.vision.spam.classifier.SpamClassification.Spam
import dev.vision.spam.mailbox.model.Email

@Composable
fun EmailItem(email: Email, classification: SpamClassification?, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = SpaceBetween,
        modifier = modifier.padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = CenterVertically
    ) {
        Column {
            Text(email.topic, style = typography.bodyLarge)
            Text(email.from, style = typography.bodySmall)
        }

        AnimatedContent(
            targetState = classification,
            label = "Email item spam status",
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) {
            when (it) {
                null -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )

                Spam -> Icon(
                    Icons.Rounded.Clear,
                    contentDescription = "This message is spam",
                    tint = colorScheme.error
                )

                Ham -> Icon(
                    Icons.Rounded.Check,
                    contentDescription = "This message is not spam",
                    tint = colorScheme.secondary
                )
            }
        }
    }
}